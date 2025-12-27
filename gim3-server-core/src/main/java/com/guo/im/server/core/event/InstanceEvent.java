package com.guo.im.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InstanceEvent extends IMEvent {

    private final String instanceId;

}
