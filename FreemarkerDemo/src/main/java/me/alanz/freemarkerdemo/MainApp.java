/*
 * Copyright 2016 Alan.
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
package me.alanz.freemarkerdemo;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alan
 */
public class MainApp {

    public static void main(String[] args) {
        Configuration cfg = loadConfig();
        Map<String, Object> root = createDataModel();
        output(cfg, root);
    }

    /**
     * 加载Freemarker配置
     *
     * @return
     */
    private static Configuration loadConfig() {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.22) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        //指定文件目录来加载模版
        // cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
        //根据给的类的位置和相对包名来加载模版
        //cfg.setClassForTemplateLoading(MainApp.class, "templates");
        //根据ClassLoader和完整的包名来加载模版
        cfg.setClassLoaderForTemplateLoading(MainApp.class.getClassLoader(), "me/alanz/freemarkerdemo/templates");

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }

    /**
     * 创建数据模型
     *
     * @return
     */
    private static Map<String, Object> createDataModel() {
        // Create the root hash
        Map<String, Object> root = new HashMap<>();
        // Put string ``user'' into the root
        root.put("user", "Big Joe");
        // Create the hash for ``latestProduct''
        Map<String, Object> latest = new HashMap<>();
        // and put it into the root
        root.put("latestProduct", latest);
        // put ``url'' and ``name'' into latest
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        return root;
    }

    /**
     * 根据模版和数据模型，输出整合处理后的内容到控制台
     *
     * @param cfg
     * @param root
     */
    private static void output(Configuration cfg, Map<String, Object> root) {
        try {
            Template temp = cfg.getTemplate("test.ftl");

            Writer out = new OutputStreamWriter(System.out);
            temp.process(root, out);
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
