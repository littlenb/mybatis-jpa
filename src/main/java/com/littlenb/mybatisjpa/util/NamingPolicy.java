package com.littlenb.mybatisjpa.util;

/**
 * @author sway.li
 * @since 2.5.0
 */
public enum NamingPolicy implements NamingStrategy {

    IDENTITY(){
        @Override
        public String translate(String name) {
            return name;
        }
    },
    LOWER_CASE_WITH_UNDERSCORES(){
        @Override
        public String translate(String name) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < name.length(); ++i) {
                char ch = name.charAt(i);
                if (ch >= 'A' && ch <= 'Z') {
                    char ch_ucase = (char) (ch + 32);
                    if (i > 0) {
                        buf.append('_');
                    }
                    buf.append(ch_ucase);
                } else {
                    buf.append(ch);
                }
            }
            return buf.toString();
        }
    },
    UPPER_CASE_WITH_UNDERSCORES(){
        @Override
        public String translate(String name) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < name.length(); ++i) {
                char ch = name.charAt(i);
                if (ch >= 'a' && ch <= 'z') {
                    char ch_ucase = (char) (ch - 32);

                    buf.append(ch_ucase);
                } else if(ch >= 'A' && ch <= 'Z'){
                    if (i > 0) {
                        buf.append('_');
                    }
                    buf.append(ch);
                } else{
                    buf.append(ch);
                }
            }
            return buf.toString();
        }
    }

}
