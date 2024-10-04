# illumio

# Flow Log Parser

## Overview

The **Flow Log Parser** is a Java application designed to parse network flow logs and apply tagging based on a predefined lookup table. It reads flow logs from a specified input file, matches them with tags from a CSV lookup table, and generates an output file containing tag counts and port/protocol combination counts.

## Problem Statement

In network monitoring, flow logs are generated to provide information about the traffic passing through a network device. However, these logs can be challenging to interpret without a tagging mechanism that associates specific traffic with meaningful labels. This project aims to automate the tagging of network flows based on a lookup table, simplifying the analysis of network traffic.

## Assumptions

1. **Default Log Format**: The program only supports the default flow log format. It does not handle custom log formats.
2. **Supported Version**: Only version 2 of the flow log format is supported. Ensure that the input logs conform to this version.
3. **Protocol Mapping**: The program only maps specific protocol numbers (1 for ICMP, 6 for TCP, and 17 for UDP). Any other protocol numbers will be treated as "unknown."
4. **Lookup Table Format**: The lookup table must be in CSV format with three columns: `dstPort`, `protocol`, and `tag`. The first row should be a header.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- A text file containing flow logs (format: whitespace-separated)
- A CSV file containing the lookup table (format: `dstPort,protocol,tag`)

## Getting Started

```bash
1. **Clone the repository**:
git clone https://github.com/DeepShah1108/illumio.git

2. **Navigate to the Project Folder:**
cd cloneDirName/illumio/illumio/Assesment/src

3. **Compile the Java Program:**
javac FlowLogParser.java

4. **Run the Java Program:**
java FlowLogParser

5. **Check the Output:**
After running the program, the results will be written to the `resources/output.txt` file.
 ```

## Tests Conducted
- The program has been tested with sample flow logs and corresponding lookup tables.
- Validations include:
- Correct tag assignment based on lookup table.
- Accurate counting of port/protocol combinations.
- Handling of untagged logs.

## Additional Notes
- Ensure that the `resources` directory contains the necessary input files (`flow_logs.txt` and `lookup_table.csv`) before running the program.
- Modify the `resources/output.txt` file location in the source code if a different output path is required.

```
