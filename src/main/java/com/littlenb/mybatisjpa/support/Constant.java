package com.littlenb.mybatisjpa.support;

import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

/**
 * @author sway.li
 */
public interface Constant {

  LanguageDriver XML_LANGUAGE_DRIVER = new XMLLanguageDriver();

  String DEFAULT_KEY_GENERATOR = "defaultKeyGenerator";

  String CERTAINTY_ENTITY_KEY = "entity";

  int DEFAULT_CASE_MODE =0;

  int UPPER_CASE_MODE = 1;

  int LOWER_CASE_MODE = 2;

}
