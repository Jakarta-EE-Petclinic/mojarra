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

package com.sun.faces.test.servlet.sessionscope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * A ViewScoped bean testing session invalidation functionality.
 */
@ManagedBean(name = "invalidatedBean")
@SessionScoped
public class InvalidatedBean {

    /**
     * Stores the text.
     */
    private String text;

    /**
     * Constructor.
     */
    public InvalidatedBean() {
        this.text = "This is from the constructor";
    }

    /**
     * Post-construct.
     *
     */
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove("count");
        this.text = "This is from the @PostConstruct";
    }

    /**
     * Pre-destroy
     */
    @PreDestroy
    public void destroy() {
        /*
         * For the purpose of the test we can actually ask for the current 
         * instance of the FacesContext, because we trigger invalidating of the 
         * session through a JSF page, however in the normal case of session 
         * invalidation this will NOT be true. So this means that normally the 
         * @PreDestroy annotated method should not try to use 
         * FacesContext.getCurrentInstance().
         */
        if (FacesContext.getCurrentInstance() != null) {
            Integer count = 0;
            if (FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().containsKey("count")) {
                count = (Integer) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("count");
            }
            count++;
            FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("count", count);
        }
    }

    /**
     * Get the text.
     */
    public String getText() {
        return this.text;
    }
}
