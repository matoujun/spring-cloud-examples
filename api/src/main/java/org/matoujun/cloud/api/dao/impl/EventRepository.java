package org.matoujun.cloud.api.dao.impl;

import org.matoujun.cloud.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**

 * User: matoujun
 *
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
