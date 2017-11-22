package com.compuware.jenkins.common.configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//import org.apache.commons.validator.routines.UrlValidator;
import org.kohsuke.stapler.QueryParameter;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class CESConnection  extends AbstractDescribableImpl<CESConnection>
{
	private String url;
	private CESToken[] tokens;

	public CESConnection() 
	{
		//do nothing
	}

	public CESConnection(String url, CESToken[] tokens) 
	{
		this.url = url;
		this.tokens = tokens;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public CESToken[] getTokens() 
	{
		return tokens;
	}

	public void setTokens(CESToken[] tokens) 
	{
		this.tokens = tokens;
	}

	@Override
	public String toString() 
	{
		ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * DescriptorImpl is used to create instances of <code>CodeCoverageBuilder</code>. It also contains the global configuration
	 * options as fields, just like the <code>CodeCoverageBuilder</code> contains the configuration options for a job
	 */
	@Extension
	public static class DescriptorImpl extends Descriptor<CESConnection>
	{
		/**
		 * Constructor.
		 */
		public DescriptorImpl()
		{
			// Do nothing
		}

		/**
		 * Constructor.
		 *
		 * @param clazz
		 *            the concrete <code>CESConnection</code> class
		 */
		public DescriptorImpl(Class<? extends CESConnection> clazz)
		{
			super(clazz);
		}

		/* 
		 * (non-Javadoc)
		 * @see hudson.model.Descriptor#getDisplayName()
		 */
		@Override
		public String getDisplayName()
		{
			return "CESConnection";
		}

		/**
		 * Validation for the 'CES URL' text field.
		 * 
		 * @param value
		 *            value passed from the config.jelly "cesUrl" field
		 * 
		 * @return validation message
		 */
		public FormValidation doCheckCesUrl(@QueryParameter String value)
		{
			FormValidation result = FormValidation.ok();

			if (StringUtils.isNotBlank(value))
			{
				//verify if url is valid
				if (!value.endsWith("/")) //$NON-NLS-1$
				{
					value = value + "/"; //$NON-NLS-1$
				}
				try
				{
					new URL(value);
				}
				catch (MalformedURLException e)
				{
					result = FormValidation.error(Messages.checkCesUrlInvalidError());
				}
			}

			return result;
		}
	}

}