package com.jsp.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;

public class CustomConfigurableSiteMeshFilter extends ConfigurableSiteMeshFilter {
	
	  @Override
	    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
	        builder.addTagRuleBundle(new Sm2TagRuleBundle());
	    }
}
