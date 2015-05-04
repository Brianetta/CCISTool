#!/bin/bash
tail -n +2 test1.csv | awk -f generate_YoungPersonsRecord.awk | cat test1.head - test.tail > test1.xml
tail -n +2 test2.csv | awk -f generate_YoungPersonsRecord.awk | cat test2.head - test.tail > test2.xml
