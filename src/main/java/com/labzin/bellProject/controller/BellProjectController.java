package com.labzin.bellProject.controller;

import com.labzin.bellProject.service.BellProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BellProjectController {

private final BellProjectService bellProjectService;


}
