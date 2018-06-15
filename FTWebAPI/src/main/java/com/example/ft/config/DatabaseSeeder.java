package com.example.ft.config;

import com.example.ft.entity.application.Application;
import com.example.ft.entity.application.ApplicationRepository;
import com.example.ft.entity.rule.Rule;
import com.example.ft.entity.rule.RuleRepository;
import com.example.ft.entity.toggle.Toggle;
import com.example.ft.entity.toggle.ToggleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder {

    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        // Delete all
        toggleRepository.deleteAll();
        applicationRepository.deleteAll();

        // Seed toggles
        Toggle carousel = Toggle.builder()
                .name("product-carousel")
                .label("Product Carousel")
                .description("Adds a carousel on the product display page")
                .enabled(false)
                .build();

        Toggle shiny = Toggle.builder()
                .name("shiny-buttons")
                .label("Shiny Buttons")
                .description("Makes every button shine like there's no tomorrow")
                .enabled(true)
                .build();

        toggleRepository.saveAll(Arrays.asList(carousel, shiny));

        // Seed applications
        Application mainStore = Application.builder()
                .name("main-store")
                .label("Main Store")
                .version("1.0")
                .username("ms1.0")
                .password("ms")
                .build();

        Application mainStore11 = Application.builder()
                .name("main-store")
                .label("Main Store")
                .version("1.1")
                .username("ms1.1")
                .password("ms")
                .build();

        Application sideProject = Application.builder()
                .name("side-project")
                .label("Side Project")
                .version("1.0")
                .username("sp1.0")
                .password("sp")
                .build();

        applicationRepository.saveAll(Arrays.asList(mainStore, mainStore11, sideProject));

        ruleRepository.saveAll(Arrays.asList(
                new Rule(mainStore, carousel, true, false),
                new Rule(mainStore11, carousel, true, false),
                new Rule(mainStore11, shiny, true, false),
                new Rule(sideProject, shiny, true, false)
        ));
    }

}
