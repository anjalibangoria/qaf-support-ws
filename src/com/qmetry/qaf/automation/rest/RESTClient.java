/*******************************************************************************
  QMetry Automation Framework provides a powerful and versatile platform to author 
  Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven approach
                 
  Copyright 2016 Infostretch Corporation
 
  This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 
  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
  OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE
 
  You should have received a copy of the GNU General Public License along with this program in the name of LICENSE.txt in the root folder of the distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 
  See the NOTICE.TXT file in root folder of this source files distribution 
  for additional information regarding copyright ownership and licenses
  of other open source software / files used by QMetry Automation Framework.
 
  For any inquiry or need additional information, please contact support-qaf@infostretch.com
 *******************************************************************************/

package com.qmetry.qaf.automation.rest;

import java.util.Collection;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import com.qmetry.qaf.automation.util.StringUtil;
import com.qmetry.qaf.automation.ws.rest.RestTestBase;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author amit.bhoraniya
 *
 */
public class RESTClient {
	public static void doExecute(RestRequestBean bean) {
		WebResource resource = new RestTestBase().getClient().resource(bean.getUri());
		Builder builder = resource.getRequestBuilder();
		for (Entry<String, Collection<String>> entry : bean.getHeaders().entrySet()) {
			Collection<String> values = entry.getValue();
			for (String value : values) {
				builder.header(entry.getKey(), value);
			}
		}
		if (StringUtil.isNotBlank(bean.getBody())) {
			builder.method(bean.getMethod(), ClientResponse.class, bean.getBody());
		} else {
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			for (Entry<String, Collection<String>> entry : bean.getParameters().entrySet()) {
				Collection<String> values = entry.getValue();
				for (String value : values) {
					formData.add(entry.getKey(), value);
				}
			}
			builder.method(bean.getMethod(), ClientResponse.class, formData);
		}
	}
}
