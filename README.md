# TimetableEvolution
Evolutionary algorithms vs Scheduling!

Java implementation of EA.

Evolved entities: timetable options
Fitness definition: score based on how many students visit lectures at convenient time
Fitness penalty: significant penalty when two courses collide, forcing one Room/Prof/Group to be at two or more lectures at the same time.

Mutation: try a different room / timeslot for a given class
Crossover: take rooms/ timeslots for a class from either 1 of two parents

Also investigated: hill climbing algorithm
