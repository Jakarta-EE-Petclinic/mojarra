/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.test.glassfish.admingui;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import java.util.Iterator;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AdminGUIIT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    @Test
    public void testAppHasDeployForm() throws Exception {
        HtmlPage page = null;
        HtmlSubmitInput button;

        CookieManager cm = webClient.getCookieManager();
        cm.clearCookies();

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setTimeout(6000000);

        String url = "http://localhost:4848/";

        for (int i = 0; i < 10; i++) {
            try {
                page = webClient.getPage(url + "common/index.jsf?bare=true");
                System.out.println("line 89");
                System.out.println(page.asXml());
            } catch (Exception e) {
                try {
                    page = webClient.getPage(url + "common/index.jsf");
                    System.out.println("line 93");
                    System.out.println(page.asXml());
                } catch (Exception e2) {
                    page = null;
                }
            }

            if (page != null) {
                HtmlElement element = page.getHtmlElementById("Login.username");
                if (element != null) {
                    break;
                }
            }
            Thread.sleep(30000);
        }

        /** 20150930-edburns
        page.getHtmlElementById("Login.username").type("admin");
        page.getHtmlElementById("Login.password").type("adminadmin");
        */
        page = page.getHtmlElementById("loginButton").click();

        Cookie jSessionID = cm.getCookie("JSESSIONID");
        Cookie c1 = new Cookie("", "_common_applications_uploadFrame.jsf", "left:0&top:0&badCookieChars:%28%2C%29%2C%3C%2C%3E%2C@%2C%2C%2C%3B%2C%3A%2C%5C%2C%22%2C/%2C%5B%2C%5D%2C%3F%2C%3D%2C%7B%2C%7D%2C%20%2C%09; treeForm_tree-hi=treeForm:tree:applications; JSESSIONID=" + jSessionID.getValue());
        cm.addCookie(c1);

        page = webClient.getPage(url + "common/applications/applications.jsf?bare=true");

        System.out.println(page.asXml());
        
        /***
        HtmlSubmitInput deployButton = page.getHtmlElementById("propertyForm:deployTable:topActionsGroup1:deployButton");
        page = deployButton.click();
        
        DomNodeList<DomElement> forms = page.getElementsByTagName("form");
        Iterator<DomElement> formIter = forms.iterator();
        boolean foundUplaodForm = false;
        while (formIter.hasNext()) {
            DomElement cur = formIter.next();
            String actionAttr = cur.getAttribute("action");
            if (null != actionAttr && actionAttr.contains("upload")) {
                foundUplaodForm = true;
            }
        }
        assertTrue(foundUplaodForm);
        ***/
    }
}
