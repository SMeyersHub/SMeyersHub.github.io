#!/bin/awk -f

# Awk HTML program
BEGIN {
  FPAT = "([^,]+)|(\"[^\"]+\")"
  print "<html>"
  print "<head>"
  print "<title>Single Family Homes</title>"
  print "</head>"
  print "<body>"
  print "<h1>Single Family Homes</h1>"
  print "<table>"
}
{if (tolower($1) == "issuedate") {print "<tr>", "<th>", $1, "</th>","<th>", $5, "</th>","<th>", $6, "</th>","<th>", $7, "</th>","<th>", $8, "</th>", "</tr>"}}
{if (tolower($3) == "single family dwelling") {print "<tr>", "<td>", $1, "</td>","<td>", $5, "</td>","<td>", $6, "</td>","<td>", $7, "</td>","<td>", $8, "</td>", "</tr>"}}
{if ($3 == "Single Family Dwelling - UNIT 280") {print "<tr>", "<td>", $1, "</td>","<td>", $5, "</td>","<td>", $6, "</td>","<td>", $7, "</td>","<td>", $8, "</td>", "</tr>"}}

END {
  print "</table>"
  print "</body>"
  print "</html>"
}