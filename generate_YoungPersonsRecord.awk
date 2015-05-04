BEGIN {FS=","}
{
print "    <YoungPersonsRecord>"
print "        <PersonalDetails>"
print "            <YoungPersonsID>"$1"</YoungPersonsID>"
print "            <CohortStatus>"$2"</CohortStatus>"
print "            <GivenName>"$3"</GivenName>"
print "            <MiddleName></MiddleName>"
print "            <FamilyName>"$4"</FamilyName>"
print "            <AddressLine1></AddressLine1>"
print "            <AddressLine2>"$8"</AddressLine2>"
print "            <AddressLine3>"$9"</AddressLine3>"
print "            <AddressLine4></AddressLine4>"
print "            <Town>"$11"</Town>"
print "            <County>"$12"</County>"
print "            <Postcode>"$13"</Postcode>"
print "            <Gender>"$5"</Gender>"
print "            <DOB>"$6"</DOB>"
print "            <Ethnicity>"$14"</Ethnicity>"
print "            <LeadLEA>"$15"</LeadLEA>"
print "            <EducatedLEA>"$16"</EducatedLEA>"
print "            <TransferredToLACode />"
print "            <LEACodeAtYear11>"$18"</LEACodeAtYear11>"
print "            <UniqueLearnerNo>"$19"</UniqueLearnerNo>"
print "            <UniquePupilNumber>"$20"</UniquePupilNumber>"
print "            <GuaranteeStatusIndicator>"$21"</GuaranteeStatusIndicator>"
print "            <YouthContractIndicator>"$22"</YouthContractIndicator>"
print "            <YouthContractStartDate>"$23"</YouthContractStartDate>"
print "            <PreviousYPIDIdentifier>"$24"</PreviousYPIDIdentifier>"
print "        </PersonalDetails>"
print "        <SeptemberGuarantee />"
print "        <LevelOfNeed>"
print "            <LevelOfNeedCode>"$25"</LevelOfNeedCode>"
print "            <SENDFlag>"$26"</SENDFlag>"
if ($40 != "") {
print "            <Characteristics>"
print "                <CharacteristicCode>"$40"</CharacteristicCode>"
print "            </Characteristics>"
}
print "        </LevelOfNeed>"
print "        <Activities>"
print "            <Cohort>"
print "                <ActivityCode>"$27"</ActivityCode>"
print "                <StartDate>"$28"</StartDate>"
print "                <DateAscertained>"$29"</DateAscertained>"
print "                <DateVerified>"$30"</DateVerified>"
print "                <ReviewDate>"$31"</ReviewDate>"
print "                <DueToLapseDate>"$32"</DueToLapseDate>"
print "                <CurrencyLapsed>"$33"</CurrencyLapsed>"
print "                <EstablishmentNumber>"$34"</EstablishmentNumber>"
print "                <EstablishmentName>"$35"</EstablishmentName>"
print "                <UKProviderReferenceNumber>"$36"</UKProviderReferenceNumber>"
print "                <NeetStartDate>"$37"</NeetStartDate>"
print "                <PredictedEndDate>"$38"</PredictedEndDate>"
print "            </Cohort>"
print "        </Activities>"
print "        <IntendedDestination>"
print "            <IntendedDestinationYr11></IntendedDestinationYr11>"
print "        </IntendedDestination>"
print "    </YoungPersonsRecord>"
}
