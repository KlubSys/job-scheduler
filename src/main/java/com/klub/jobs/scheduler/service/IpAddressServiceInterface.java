package com.klub.jobs.scheduler.service;

import javax.servlet.http.HttpServletRequest;

public interface IpAddressServiceInterface {

    String getClientIp(HttpServletRequest request);
}
