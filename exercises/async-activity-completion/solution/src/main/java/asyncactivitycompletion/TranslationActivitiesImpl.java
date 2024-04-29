package asyncactivitycompletion;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import io.temporal.activity.Activity;
import io.temporal.failure.ApplicationFailure;
import java.net.HttpURLConnection;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;
import asyncactivitycompletion.model.TranslationActivityInput;
import asyncactivitycompletion.model.TranslationActivityOutput;

public class TranslationActivitiesImpl implements TranslationActivities {

  private static final Logger logger = LoggerFactory.getLogger(TranslationActivitiesImpl.class);

  @Override
  public TranslationActivityOutput translateTerm(TranslationActivityInput input) {

    logger.info("translateTerm Activity received input: {}", input);

    // Get the activity execution context
    ActivityExecutionContext context = Activity.getExecutionContext();

    // Set a correlation token that can be used to complete the activity asynchronously
    byte[] taskToken = context.getTaskToken();

    String encoded = new String(Base64.getEncoder().encode(taskToken));

    logger.info("TASK TOKEN: {}", encoded);

    String term = input.getTerm();
    String lang = input.getLanguageCode();

    logger.info("[ACTIVITY INVOKED] translateTerm invoked with input term: {} language code: {}",
        term, lang);

    // construct the URL, with supplied input parameters, for accessing the
    // microservice
    URL url = null;
    try {
      String baseUrl = "http://localhost:9999/translate?term=%s&lang=%s";
      url = URI.create(
          String.format(baseUrl,
              URLEncoder.encode(term, "UTF-8"),
              URLEncoder.encode(lang, "UTF-8")))
          .toURL();
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw Activity.wrap(e);
    }

    TranslationActivityOutput result = new TranslationActivityOutput();

    try {
      // Open a connection to the URL
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set the HTTP request method (GET, POST, etc.)
      connection.setRequestMethod("GET");

      // Get the response code
      int responseCode = connection.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) {
        // If the response code is 200 (HTTP OK), the request was successful
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
          response.append(line);
        }

        reader.close();

        connection.disconnect();
        result.setTranslation(response.toString());

      } else {
        // If the response code is not 200, there was an error
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream()));
        String line;
        StringBuilder errorResponse = new StringBuilder();

        while ((line = errorReader.readLine()) != null) {
          errorResponse.append(line);
        }

        errorReader.close();

        connection.disconnect();
        // Print the error response
        throw ApplicationFailure.newFailure(errorResponse.toString(), IOException.class.getName());
      }

    } catch (IOException e) {
      logger.error(e.getMessage());
      throw Activity.wrap(e);
    }

    logger.info("Translation Service returned: {}", result.getTranslation());

    context.doNotCompleteOnReturn();

    // Since we have set doNotCompleteOnReturn(), the return value is ignored.
    return new TranslationActivityOutput("this will be ignored");
  }

}
