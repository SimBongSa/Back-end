//package com.simbongsa.Backend.configuration;
//
//import org.jsoup.Jsoup;
//import org.jsoup.safety.Whitelist;
//import org.owasp.esapi.ESAPI;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Enumeration;
//import java.util.List;
//
//
//public class XSSRequestWrapper extends HttpServletRequestWrapper {
//
//    public XSSRequestWrapper(HttpServletRequest request) {
//        super(request);
//    }
//
//    @Override
//    public String[] getParameterValues(String parameter) {
//        String[] values = super.getParameterValues(parameter);
//        if (values == null) {
//            return null;
//        }
//        int count = values.length;
//        String[] encodedValues = new String[count];
//        for (int i = 0; i < count; i++) {
//            encodedValues[i] = stripXSS(values[i]);
//        }
//        return encodedValues;
//    }
//    @Override
//    public String getParameter(String parameter) {
//        String value = super.getParameter(parameter);
//        return stripXSS(value);
//    }
//
//    @Override
//    public Enumeration getHeaders(String name) {
//        List result = new ArrayList<>();
//        Enumeration headers = super.getHeaders(name);
//        while (headers.hasMoreElements()) {
//            String header = (String) headers.nextElement();
//            String[] tokens = header.split(",");
//            for (String token : tokens) {
//                result.add(stripXSS(token));
//            }
//        }
//        return Collections.enumeration(result);
//    }
//
//    public static String stripXSS(String value) {
//        if (value == null) {
//            return null;
//        }
//        value = ESAPI.encoder()
//                .canonicalize(value)
//                .replaceAll("\0", "");
//        return Jsoup.clean(value, Whitelist.none());
//    }
//}