package com.customer.queue.constants;

public class WebSocketPushConstants {
  
  //Topics
  public static final String StatisticsTopic ="/topic/statistics";
  public static final String AnnouncementTopic ="/topic/announcement";
  public static final String QueueDepthTopic ="/topic/queuedepth";
  public static final String TableData ="/topic/tabledata";
  
  //Keys used in WebSocketPush for AverageServiceCompletionTime
  public static final String TotalNumberServicedKey = "-TotalNumberServiced";
  public static final String AverageServiceCompletionTimeKey="-AverageServiceCompletionTime";
}
