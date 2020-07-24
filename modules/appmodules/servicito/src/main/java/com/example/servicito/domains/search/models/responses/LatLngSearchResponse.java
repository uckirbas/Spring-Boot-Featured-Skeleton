package com.example.servicito.domains.search.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.servicito.domains.address.models.dto.LatLngDto;

import java.util.List;

public class LatLngSearchResponse {
    private String query;
    @JsonProperty("query_latlng")
    private LatLngDto queryLatLng;
    private List<LatLngDto> results;

    public LatLngSearchResponse() {
    }

    public LatLngSearchResponse(String query, LatLngDto queryLatLng, List<LatLngDto> results) {
        this.query = query;
        this.queryLatLng = queryLatLng;
        this.results = results;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public LatLngDto getQueryLatLng() {
        return queryLatLng;
    }

    public void setQueryLatLng(LatLngDto queryLatLng) {
        this.queryLatLng = queryLatLng;
    }

    public List<LatLngDto> getResults() {
        return results;
    }

    public void setResults(List<LatLngDto> results) {
        this.results = results;
    }
}
