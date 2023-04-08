import { Scenario } from "src/app/shared/scenario.model"

export const SCENARIOS = new Array(new Scenario("landuff-map-picture.jpg", "Landuff", "Landuff Town is a small town with a very friendly town council. They want to work with you in providing an efficient and effective transport service for Landuff Town.",
                            new Array("Serve all bus stops in Landuff.", "Ensure a frequent service on all routes.", "Ensure that passenger satisfaction remains above 70% at all times." )),
                                new Scenario("longtscity-map-picture.jpg", "Longts City", "Longts City is a very large city. The city council are suspicious of your new company and you will need to impress them very quickly in order to establish a good working relationship.",
                            new Array("Serve all bus stops in Longts.", "Ensure a very frequent service on all routes.", "Ensure that passenger satisfaction remains above 50% at all times.")),
                                new Scenario("mdorf-map-picture.jpg", "MDorf", "Millenium Dorf City is a small city. The city council are prepared to work with you providing that you can meet their targets within their timescales.",
                            new Array("Serve all bus stops in MDorf.", "Ensure a frequent service on all routes.", "Ensure that passenger satisfaction remains above 35% at all times.")));