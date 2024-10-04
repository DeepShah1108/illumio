import java.io.*;
import java.util.*;

public class FlowLogParser {

    public static void main(String[] args) {
        String flowLogFile = "resources/flow_logs.txt";
        String lookupFile = "resources/lookup_table.csv";
        String outputFile = "resources/output.txt";

        try {
            // Load the lookup table
            Map<String, String> lookupTable = loadLookupTable(lookupFile);

            // Parse the flow logs and generate output
            parseFlowLogs(flowLogFile, lookupTable, outputFile);

            System.out.println("Results written to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write the results to the output file
    public static void writeOutput(Map<String, Integer> tagCounts, Map<String, Integer> portProtocolCounts, int untaggedCount, String outputFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        // Write Tag Counts
        writer.write("Tag Counts:\n");
        writer.write("Tag,Count\n");
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue() + "\n");
        }

        // Write Port/Protocol Combination Counts
        writer.write("\nPort/Protocol Combination Counts:\n");
        writer.write("Port,Protocol,Count\n");
        for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
            String[] parts = entry.getKey().split("_");
            writer.write(parts[0] + "," + parts[1] + "," + entry.getValue() + "\n");
        }

        writer.close();
    }

    // Helper method to get protocol name from protocol number
    public static String getProtocol(String protocolNumber) {
        switch (protocolNumber) {
            case "6":
                return "tcp";
            case "17":
                return "udp";
            case "1":
                return "icmp";
            default:
                return "unknown";
        }
    }

    // Method to parse flow logs and apply tags
    public static void parseFlowLogs(String flowLogFilePath, Map<String, String> lookupTable, String outputFilePath) throws IOException {
        Map<String, Integer> tagCounts = new HashMap<>();
        Map<String, Integer> portProtocolCounts = new HashMap<>();
        int untaggedCount = 0;

        BufferedReader reader = new BufferedReader(new FileReader(flowLogFilePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");

            String dstPort = parts[6];
            String protocol = getProtocol(parts[7]); // Map protocol number to 'tcp', 'udp', 'icmp'
            String key = dstPort + "_" + protocol;

            // Determine the tag
            String tag = lookupTable.getOrDefault(dstPort + "_" + protocol, "Untagged");

            // Update tag counts
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);

            // Update port/protocol combination counts
            portProtocolCounts.put(key, portProtocolCounts.getOrDefault(key, 0) + 1);

            if (tag.equals("Untagged")) {
                untaggedCount++;
            }
        }

        reader.close();

        // Write output to file
        writeOutput(tagCounts, portProtocolCounts, untaggedCount, outputFilePath);
    }

    // Method to load the lookup table into a Map
    public static Map<String, String> loadLookupTable(String lookupFilePath) throws IOException {
        Map<String, String> lookupTable = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(lookupFilePath));
        String line;

        // Skip header
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String dstPort = parts[0].trim();
            String protocol = parts[1].trim().toLowerCase();
            String tag = parts[2].trim();
            lookupTable.put(dstPort + "_" + protocol, tag);
        }

        reader.close();
        return lookupTable;
    }
}
