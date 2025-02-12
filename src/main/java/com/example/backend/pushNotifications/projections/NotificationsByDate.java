package com.example.backend.pushNotifications.projections;

import com.example.backend.util.Client;

@Client
public interface NotificationsByDate {
  String getDate();
  long getSent();
  long getDelivered();
}
