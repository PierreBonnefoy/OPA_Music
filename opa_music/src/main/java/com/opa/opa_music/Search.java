package com.opa.opa_music;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Save SearchSpace's String
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Component
public class Search {
    public String search;
}
