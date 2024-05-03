package customsearchattributes;

import io.temporal.common.SearchAttributeKey;

public class Constants {

  public static final String TASK_QUEUE_NAME = "pizza-tasks";
  public static final SearchAttributeKey<Boolean> IS_ORDER_FAILED = SearchAttributeKey.forBoolean("isOrderFailed");

}
