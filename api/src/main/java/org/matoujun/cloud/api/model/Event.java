package org.matoujun.cloud.api.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.netty.util.internal.StringUtil;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.matoujun.cloud.common.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author matoujun
 */
@Slf4j
@Data
@Entity(name = "Event")
@Table(name = "cloud_event", indexes = {
        @Index(name = "idx_event_origin", columnList = "origin")
}
)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class Event {
    @Id
    @Column(name = "id", columnDefinition = "bigint(20) unsigned NOT NULL AUTO_INCREMENT comment " +
            "'primary key'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_title", columnDefinition = "varchar(200) comment " +
            "'event title'")
    private String eventTitle = StringUtil.EMPTY_STRING;

    @Column(name = "origin", columnDefinition = "int(8) NOT NULL DEFAULT '-1' comment 'event " +
            "source'")
    private Integer origin = -1;

    @Column(name = "create_time", columnDefinition = "datetime NOT NULL DEFAULT '2001-01-01 " +
            "00:00:00' comment 'create time'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = TimeUtil.parseDayTime("2001-01-01 00:00:00");

    @Column(name = "update_time", columnDefinition = "datetime NOT NULL DEFAULT '2001-01-01 " +
            "00:00:00' comment 'update time'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime = TimeUtil.parseDayTime("2001-01-01 00:00:00");

    @Type(type = "json")
    @Column(name = "tags", columnDefinition = "json comment 'event tags'")
    private List<Tag> tags;

}
