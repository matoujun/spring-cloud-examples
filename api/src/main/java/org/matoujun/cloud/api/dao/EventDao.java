package org.matoujun.cloud.api.dao;

import org.matoujun.cloud.api.model.Event;

import java.util.List;

/**
 * @author matoujun

 */
public interface EventDao{
  Event save(Event event);
}
