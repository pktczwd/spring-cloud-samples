package org.pankai.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pktczwd on 2016/10/31.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

    private Long refreshInterval = 60L;//seconds
    private Long limit;
    private List<Type> type = new ArrayList<>();

    public enum Type {
        ORIGIN, USER
    }
}
