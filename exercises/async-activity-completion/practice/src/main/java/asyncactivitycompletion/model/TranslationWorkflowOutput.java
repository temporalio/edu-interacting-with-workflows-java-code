package asyncactivitycompletion.model;

public class TranslationWorkflowOutput {

  private String helloMessage;

  public TranslationWorkflowOutput() {
  }

  public TranslationWorkflowOutput(String helloMessage) {
    this.helloMessage = helloMessage;
  }

  public String getHelloMessage() {
    return helloMessage;
  }

  public void setHelloMessage(String helloMessage) {
    this.helloMessage = helloMessage;
  }

  @Override
  public String toString() {
    return this.helloMessage;
  }
}
