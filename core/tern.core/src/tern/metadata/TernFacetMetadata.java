/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package tern.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tern.server.ITernFacet;
import tern.server.protocol.JsonHelper;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Tern facet metadata loded from *.metadata.json file.
 *
 */
public class TernFacetMetadata {

	private static final String NAME_FIELD = "name";
	private static final String DESCRIPTION_FIELD = "description";
	private static final String HOMEPAGE_FIELD = "homepage";
	private static final String AUTHOR_FIELD = "author";
	private static final String REPOSITORY_FIELD = "repository";
	private static final String BUGS_FIELD = "bugs";
	private static final String HELP_FIELD = "help";
	private static final String URL_FIELD = "url";
	private static final String DEPENDENCIES_FIELD = "dependencies";
	private static final String OPTIONS_FIELD = "options";

	private final String name;
	private final String description;
	private final String homepage;
	private final String author;
	private final String repositoryURL;
	private final String bugsURL;
	private final String helpURL;
	private final Collection<String> dependencies;
	private final Collection<TernFacetMetadataOption> options;

	/**
	 * Create facet metadata from JSON object.
	 * 
	 * @param json
	 */
	public TernFacetMetadata(JsonObject json) {
		this.name = JsonHelper.getString(json, NAME_FIELD);
		this.description = JsonHelper.getString(json, DESCRIPTION_FIELD);
		this.homepage = JsonHelper.getString(json, HOMEPAGE_FIELD);
		this.author = JsonHelper.getString(json, AUTHOR_FIELD);
		this.repositoryURL = getURL(json, REPOSITORY_FIELD);
		this.bugsURL = getURL(json, BUGS_FIELD);
		this.helpURL = getURL(json, HELP_FIELD);
		// dependencies
		JsonValue dependencies = json.get(DEPENDENCIES_FIELD);
		if (dependencies != null && dependencies instanceof JsonArray) {
			this.dependencies = parseDependencies((JsonArray) dependencies);
		} else {
			this.dependencies = Collections.emptyList();
		}
		// options
		JsonValue options = json.get(OPTIONS_FIELD);
		if (options != null && options instanceof JsonArray) {
			this.options = parseOptions((JsonArray) options);
		} else {
			this.options = Collections.emptyList();
		}
	}

	public String getURL(JsonObject json, String name) {
		JsonValue value = json.get(name);
		if (value != null) {
			return JsonHelper.getString((JsonObject) value, URL_FIELD);
		}
		return null;
	}

	private Collection<String> parseDependencies(JsonArray jsonDependencies) {
		List<String> dependencies = new ArrayList<String>();
		for (JsonValue jsonDependency : jsonDependencies) {
			dependencies.add(JsonHelper.getString(jsonDependency));
		}
		return dependencies;
	}

	private Collection<TernFacetMetadataOption> parseOptions(
			JsonArray jsonOptions) {
		List<TernFacetMetadataOption> options = new ArrayList<TernFacetMetadataOption>();
		for (JsonValue jsonOption : jsonOptions) {
			options.add(new TernFacetMetadataOption((JsonObject) jsonOption));
		}
		return options;
	}

	/**
	 * Returns the name of the facet.
	 * 
	 * @return the name of the facet.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the description of the facet.
	 * 
	 * @return the description of the facet.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the home page of the facet.
	 * 
	 * @return the home page of the facet.
	 */
	public String getHomePage() {
		return homepage;
	}

	/**
	 * Returns the author of the facet.
	 * 
	 * @return the author of the facet.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Returns the repository URL of the facet.
	 * 
	 * @return the repository URL of the facet.
	 */
	public String getRepositoryURL() {
		return repositoryURL;
	}

	/**
	 * Returns the bugs URL of the facet.
	 * 
	 * @return the bugs URL of the facet.
	 */
	public String getBugsURL() {
		return bugsURL;
	}

	/**
	 * Returns the help URL of the facet.
	 * 
	 * @return the help URL of the facet.
	 */
	public String getHelpURL() {
		return helpURL;
	}

	/**
	 * Returns list of options.
	 * 
	 * @return list of options.
	 */
	public Collection<TernFacetMetadataOption> getOptions() {
		return options;
	}

	/**
	 * Returns list of {@link ITernFacet} name dependencies.
	 * 
	 * @return list of {@link ITernFacet} name dependencies.
	 */
	public Collection<String> getDependencies() {
		return dependencies;
	}
}
