TEMPLATE FOR RETROSPECTIVE (Team ##)
=====================================

The retrospective should include _at least_ the following
sections:

- [process measures](#process-measures)
- [quality measures](#quality-measures)
- [general assessment](#assessment)

## PROCESS MEASURES 

### Macro statistics

- Number of stories committed vs. done 

  3/0
- Total points committed vs. done 

  10/0
- Nr of hours planned vs. spent (as a team)

  97/82.3

**Remember**a story is done ONLY if it fits the Definition of Done:
 
- Unit Tests passing
- Code review completed
- Code present on VCS
- End-to-End tests performed

> Please refine your DoD if required (you cannot remove items!) 

### Detailed statistics

| Story | # Tasks | Points | Hours est. | Hours actual |
|-------|---------|--------|------------|--------------|
| _#0_  | 14      |        | 49         | 38.5         |
| _#1_  | 7       | 5      | 20         | 19.3         |
| _#2_  | 5       | 3      | 14         | 12.5         |
| _#3_  | 5       | 2      | 14         | 12           |
   

> story `#0` is for technical tasks, leave out story points (not applicable in this case)

- Hours per task average, standard deviation (estimate and actual)

 average estimate: (97/31)=3.12
 average actual: (82.3/31)=2.65
 standard deviation estimate: 2.19
 standard deviation actual: 2

- Total estimation error ratio: sum of total hours spent / sum of total hours effort - 1

    $$\frac{\sum_i spent_{task_i}}{\sum_i estimation_{task_i}} - 1$$

    res-0.15

- Absolute relative task estimation error: sum( abs( spent-task-i / estimation-task-i - 1))/n

    $$\frac{1}{n}\sum_i^n \left| \frac{spent_{task_i}}{estimation_task_i}-1 \right| $$

    res: 0.12

## QUALITY MEASURES 

- Unit Testing:
  - Total hours estimated: 0
  - Total hours spent: 0
  - Nr of automated unit test cases : 0
  - Coverage (if available) 
- E2E testing:
  - Total hours estimated 12
  - Total hours spent 9.20
- Code review 
  - Total hours estimated: 4
  - Total hours spent: 4
  


## ASSESSMENT

- What caused your errors in estimation (if any)?

  Since it's the first time we work together as a team, we didn't know very well everyone ability, so at the beginning was difficult to estimate the time needed

- What lessons did you learn (both positive and negative) in this sprint?

  We should have more meeting session to improve the cooperation, for example between the back-end and front-end team. 
  As positive we could say that we were able to deliver something.

- Which improvement goals set in the previous retrospective were you able to achieve? 
  
- Which ones you were not able to achieve? Why?

- Improvement goals for the next sprint and how to achieve them (technical tasks, team coordination, etc.)

  In the next sprint we should care more about testing and creating a documentation that helps the team coordination.

- One thing you are proud of as a Team!!

  Our application works so it's a good starting point.