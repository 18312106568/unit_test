package com.creditcloud.jpa.unit_test.designModel;

public class FactoryDesign {
    /*
    public class RuleConfigSource {
        public RuleConfig load(String ruleConfigFilePath) {
            String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
            IRuleConfigParser parser = createParser(ruleConfigFileExtension);
            if (parser == null) {
                throw new InvalidRuleConfigException(
                        "Rule config file format is not supported: " + ruleConfigFilePath);
            }

            String configText = "";
            //从ruleConfigFilePath文件中读取配置文本到configText中
            //TODO
            RuleConfig ruleConfig = parser.parse(configText);
            return ruleConfig;
        }
        private String getFileExtension(String filePath) {
            //...解析文件名获取扩展名，比如rule.json，返回json
            return "json";
        }

        private IRuleConfigParser createParser(String configFormat) {
            IRuleConfigParser parser = null;
            if ("json".equalsIgnoreCase(configFormat)) {
                parser = new JsonRuleConfigParser();
            } else if ("xml".equalsIgnoreCase(configFormat)) {
                parser = new XmlRuleConfigParser();
            } else if ("yaml".equalsIgnoreCase(configFormat)) {
                parser = new YamlRuleConfigParser();
            } else if ("properties".equalsIgnoreCase(configFormat)) {
                parser = new PropertiesRuleConfigParser();
            }
            return parser;
        }
    }

*/

    /*public interface IRuleConfigParserFactory {
        IRuleConfigParser createParser();
    }
    public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new JsonRuleConfigParser();
        }
    }
    public class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new XmlRuleConfigParser();
        }
    }
    public class YamlRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new YamlRuleConfigParser();
        }
    }
    public class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new PropertiesRuleConfigParser();
        }
    }


    public class RuleConfigSource {
        public RuleConfig load(String ruleConfigFilePath) {
            String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
            IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
            if (parserFactory == null) {
                throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
            }
            IRuleConfigParser parser = parserFactory.createParser();
            String configText = "";
            //从ruleConfigFilePath文件中读取配置文本到configText中
            RuleConfig ruleConfig = parser.parse(configText);
            return ruleConfig;
        }
        private String getFileExtension(String filePath) {
            //...解析文件名获取扩展名，比如rule.json，返回json
            return "json";
        }
    }
    //因为工厂类只包含方法，不包含成员变量，完全可以复用，
//不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。
    public static class RuleConfigParserFactoryMap { //工厂的工厂
        private static final Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();
        static {
            cachedFactories.put("json", new JsonRuleConfigParserFactory());
            cachedFactories.put("xml", new XmlRuleConfigParserFactory());
            cachedFactories.put("yaml", new YamlRuleConfigParserFactory());
            cachedFactories.put("properties", new PropertiesRuleConfigParserFactory());
        }
        public static IRuleConfigParserFactory getParserFactory(String type) {
            if (type == null || type.isEmpty()) {
                return null;
            }
            IRuleConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
            return parserFactory;
        }
    }*/
}
