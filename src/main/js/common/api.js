/*
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
// SonarRequest (referenced as sonar-request here, see the Webpack config)
// Exposes helpers for managing API requests.
import { getJSON } from "sonar-request";

export function findQualityProfilesStatistics(project) {
  return getJSON("/api/qualityprofiles/search").then(function(response) {
    return response.profiles.length;
  });
}

export function findQualityQatesStatistics(project) {
  return getJSON("/api/qualitygates/list").then(function(response) {
    return response.qualitygates.length;
  });
}

export function findIssuesStatistics(project) {
  return getJSON("/api/issues/search").then(function(response) {
    return response.total;
  });
}

export function findProjects(project) {
  return getJSON("/api/projects/search").then(function(response) {
    return response.components.length;
  });
}

export function findVersionsAndMeasures(project) {
  return getJSON("/api/project_analyses/search", {
    project: project.key,
    p: 1,
    ps: 500
  }).then(function(responseAnalyses) {
    const numberOfAnalyses = responseAnalyses.analyses.length;
    if (numberOfAnalyses > 0) {
      return getJSON("/api/measures/search_history", {
        component: project.key,
        metrics: "alert_status,bugs,vulnerabilities,sqale_index,reliability_rating,security_rating,sqale_rating",
        ps: 50
      }).then(function(responseMetrics) {
        var data = [];
        var numberOfVersions = 0;
        for (let i = 0; i < numberOfAnalyses; i++) {
          let result = {
            alert_status: "",
            bugs: "0",
            vulnerabilities: "0",
            sqale_index: "0",
            reliability_rating: "",
            security_rating: "",
            sqale_rating: ""
          };
          const numberOfMeasuresRetrieved = 7;

          for (let k = 0; k < numberOfMeasuresRetrieved; k++) {
            for (let d = 0; d < responseMetrics.measures[k].history.length; d++) {
              if (
                responseMetrics.measures[k].history[d].date === responseAnalyses.analyses[i].date
              ) {
                if (responseMetrics.measures[k].metric === "bugs") {
                  result.bugs = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "vulnerabilities") {
                  result.vulnerabilities = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "sqale_index") {
                  result.sqale_index = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "alert_status") {
                  result.alert_status = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "reliability_rating") {
                  result.reliability_rating = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "security_rating") {
                  result.security_rating = responseMetrics.measures[k].history[d].value;
                } else if (responseMetrics.measures[k].metric === "sqale_rating") {
                  result.sqale_rating = responseMetrics.measures[k].history[d].value;
                }
              }
            }
          }

          data[numberOfVersions] = result;
          numberOfVersions++;
        }
        return data;
      });
    }
  });
}
