package com.rajkovski.toni.transportdemo.services.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rajkovski.toni.transportdemo.TestUtil;
import com.rajkovski.toni.transportdemo.model.Route;
import com.rajkovski.toni.transportdemo.model.TransportRoutes;
import com.rajkovski.toni.transportdemo.model.Segment;
import com.rajkovski.toni.transportdemo.model.Stop;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link GsonDataParser}.
 */

public class GsonDataParserTest {

  private GsonDataParser gsonDataParser = new GsonDataParser(new Gson());

  @Test
  public void parseData_success_full() {
    //given
    InputStream input = TestUtil.loadResource("sample_full.json");

    //when
    TransportRoutes data = gsonDataParser.parseData(input);

    //then
    assertNotNull(data);
    assertEquals(9, data.getRoutes().size());

    Route route1 = data.getRoutes().get(0);
    assertEquals("public_transport", route1.getType());
    assertEquals("vbb", route1.getProvider());
    assertEquals(2, route1.getSegments().size());
    assertNull(route1.getProperties());
    assertEquals("EUR", route1.getPrice().getCurrency());
    assertEquals(270, route1.getPrice().getAmount());

    Segment segment1 = route1.getSegments().get(0);
    assertNull(segment1.getName());
    assertEquals(0, segment1.getNum_stops());
    assertEquals(2, segment1.getStops().size());
    assertEquals("walking", segment1.getTravel_mode());
    assertNull(segment1.getDescription());
    assertEquals("#b1becc", segment1.getColor());
    assertEquals("https://d3m2tfu2xpiope.cloudfront.net/vehicles/walking.svg",
      segment1.getIcon_url());
    assertEquals("uvr_I{yxpABuAFcAp@yHvAwNr@iGPwAh@a@jAg@", segment1.getPolyline());

    Stop stop1 = segment1.getStops().get(0);
    assertEquals(52.530227, stop1.getLat(), 0.0000001);
    assertEquals(13.403356, stop1.getLng(), 0.0000001);
    assertEquals("2015-04-17T13:30:00+02:00", stop1.getDatetime());
    assertNull(stop1.getName());
    assertNull(stop1.getProperties());

    Stop stop2 = segment1.getStops().get(1);
    assertEquals(52.528187, stop2.getLat(), 0.0000001);
    assertEquals(13.410404, stop2.getLng(), 0.0000001);
    assertEquals("2015-04-17T13:38:00+02:00", stop2.getDatetime());
    assertEquals("U Rosa-Luxemburg-Platz", stop2.getName());
    assertNull(stop2.getProperties());

    Segment segment2 = route1.getSegments().get(1);
    assertEquals("U2", segment2.getName());
    assertEquals(8, segment2.getNum_stops());
    assertEquals(9, segment2.getStops().size());
    assertEquals("subway", segment2.getTravel_mode());
    assertEquals("S+U Potsdamer Platz", segment2.getDescription());
    assertEquals("#d64820", segment2.getColor());
    assertEquals("https://d3m2tfu2xpiope.cloudfront.net/vehicles/subway.svg",
      segment2.getIcon_url());
    assertEquals("elr_I_fzpAfe@_Sf]dFr_@~UjCbg@yKvj@lFfb@`C|c@hNjc@", segment2.getPolyline());
  }

  @Test
  public void parseData_success_empty() {
    //given
    InputStream input = TestUtil.loadResource("sample_empty.json");

    //when
    TransportRoutes data = gsonDataParser.parseData(input);

    //then
    assertNotNull(data);
    assertEquals(0, data.getRoutes().size());
    assertNull(data.getProvider_attributes().getVbb());
    assertNull(data.getProvider_attributes().getDrivenow());
    assertNull(data.getProvider_attributes().getCar2go());
    assertNull(data.getProvider_attributes().getGoogle());
    assertNull(data.getProvider_attributes().getNextbike());
    assertNull(data.getProvider_attributes().getCallabike());
  }

  @Test
  public void parseData_error_null() {
    //given
    InputStream input = null;

    //when
    TransportRoutes data = gsonDataParser.parseData(input);

    //then
    assertNull(data);
  }

  @Test(expected = JsonSyntaxException.class)
  public void parseData_error_syntax() {
    //given
    InputStream input = new ByteArrayInputStream(new byte[] {23, 43, 53, 123});

    //when
    TransportRoutes data = gsonDataParser.parseData(input);

    //then
    Assert.fail("Exception expected");
  }

}
