/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.samples.tcpclientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageTimeoutException;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;
import org.springframework.integration.samples.tcpclientserver.support.CustomTestContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Demonstrates the use of a gateway as an entry point into the integration flow,
 * with two pairs of collaborating channel adapters (client and server), which
 * enables multiplexing multiple messages over the same connection.
 *
 * Requires correlation data in the payload.
 *
 * @author Gary Russell
 * @since 2.1
 *
 */
@ContextConfiguration(loader=CustomTestContextLoader.class, locations={"/META-INF/spring/integration/tcpClientServerDemo-conversion-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class TcpClientServerDemoTimeoutTest {

	@Autowired
	SimpleGateway gw;

	@Autowired
	AbstractServerConnectionFactory crLfServer;

	@Before
	public void setup() {
		TestingUtilities.waitListening(this.crLfServer, 10000L);
	}

	@Test
	public void testHappyDay() {
		String result = gw.send("999Hello world!"); // first 3 bytes is correlationid
		assertEquals("999Hello world!:echo", result);
	}
	
	@Test(expected = MessageTimeoutException.class)
	public void testTimeout() {
		String result = gw.send("TIMEOUT_TEST"); // first 3 bytes is correlationid
		System.out.println(result);
	}


}
