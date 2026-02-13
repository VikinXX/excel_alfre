# Task-Oriented Training Randomizer
<img width="2816" height="1536" alt="tree decision workflow" src="https://github.com/user-attachments/assets/4a1d1b31-69d5-436c-9846-bc0524384850" />

## Overview
This tool is designed for stroke rehabilitation research. It automates the randomization of Task-Oriented Training (TOT) exercises for patients based on their Upper-Limb Fugl-Meyer Assessment (UL-FMA) scores. By using conditional logic tied to specific UL-FMA subscales, the tool ensures that patients are only assigned exercises that are appropriate for their current level of motor recovery.

## 🎯 Features
* **Automated Randomization:** Quickly generates an individualized exercise protocol for each participant.
* **Threshold-Based Logic:** Evaluates subscale scores (Wrist, Hand, Coordination) to include or exclude specific activity groups.

---

## 🧠 Randomization Logic & Cut-off Points

The core algorithm of this tool relies on specific cut-off points extracted from the study's Task-Oriented Training table. 


<img width="2816" height="1536" alt="tree decision flowchart" src="https://github.com/user-attachments/assets/c3d597c7-1475-4182-a88d-2966a7f090de" />



Activities are divided into columns (Upper Limb, Wrist, Hand, Coordination). The randomization follows these strict inclusion rules:

| Activity Group | Inclusion Criteria | Description |
| :--- | :--- | :--- |
| **Upper Limb Activities** | **Always Included** | These activities are unconditionally added to the randomization pool for *all* participants. |
| **Wrist Activities** | **Wrist Subscale > 4** | The participant must score more than 4 points in the UL-FMA Wrist subscale. |
| **Hand Activities** | **Hand Subscale > 6** | The participant must score more than 6 points in the UL-FMA Hand subscale. |
| **Coordination Activities** | **Coordination Subscale > 2** | The participant must score more than 2 points in the UL-FMA Coordination subscale. |

> **Note for Researchers:** If a participant does not meet the minimum threshold for a specific subscale, the activities from that corresponding column are automatically excluded from their randomization pool to prevent frustration or injury.

---

## 🚀 Usage 

1. Input the participant's UL-FMA subscores into the system 
2. Run the randomization function.
3. The tool will output a tailored list of Task-Oriented Training activities for that specific patient session.

After that, you will be able to generate new exercises
