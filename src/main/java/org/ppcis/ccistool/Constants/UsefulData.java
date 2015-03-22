package org.ppcis.ccistool.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © Brian Ronald
 * 16/03/15
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class UsefulData {

    /*
        for (Map.Entry<Integer,String> LEA : UsefulData.LEA.entrySet()) {
            System.out.println(LEA.getKey().toString() + " " + LEA.getValue());
        }
     */
    public static final Map<Integer,String> LEA = new HashMap<>(153);
    static {
        LEA.put(301,"Barking and Dagenham");
        LEA.put(308,"Enfield");
        LEA.put(302,"Barnet");
        LEA.put(881,"Essex");
        LEA.put(370,"Barnsley");
        LEA.put(390,"Gateshead");
        LEA.put(800,"Bath and North East Somerset");
        LEA.put(916,"Gloucestershire");
        LEA.put(822,"Bedford");
        LEA.put(203,"Greenwich");
        LEA.put(303,"Bexley");
        LEA.put(204,"Hackney");
        LEA.put(330,"Birmingham");
        LEA.put(876,"Halton");
        LEA.put(889,"Blackburn with Darwen");
        LEA.put(205,"Hammersmith and Fulham");
        LEA.put(890,"Blackpool");
        LEA.put(850,"Hampshire");
        LEA.put(350,"Bolton");
        LEA.put(309,"Haringey");
        LEA.put(837,"Bournemouth");
        LEA.put(310,"Harrow");
        LEA.put(867,"Bracknell Forest");
        LEA.put(805,"Hartlepool");
        LEA.put(380,"Bradford");
        LEA.put(311,"Havering");
        LEA.put(304,"Brent");
        LEA.put(884,"Herefordshire");
        LEA.put(846,"Brighton and Hove");
        LEA.put(919,"Hertfordshire");
        LEA.put(801,"Bristol");
        LEA.put(312,"Hillingdon");
        LEA.put(305,"Bromley");
        LEA.put(313,"Hounslow");
        LEA.put(825,"Buckinghamshire");
        LEA.put(921,"Isle of Wight");
        LEA.put(351,"Bury");
        LEA.put(420,"Isles of Scilly");
        LEA.put(381,"Calderdale");
        LEA.put(206,"Islington");
        LEA.put(873,"Cambridgeshire");
        LEA.put(207,"Kensington and Chelsea");
        LEA.put(202,"Camden");
        LEA.put(886,"Kent");
        LEA.put(823,"Central Bedfordshire");
        LEA.put(314,"Kingston upon Thames");
        LEA.put(895,"Cheshire East");
        LEA.put(810,"Kingston upon Hull");
        LEA.put(896,"Cheshire West and Chester");
        LEA.put(382,"Kirklees");
        LEA.put(201,"City of London");
        LEA.put(340,"Knowsley");
        LEA.put(908,"Cornwall");
        LEA.put(208,"Lambeth");
        LEA.put(331,"Coventry");
        LEA.put(888,"Lancashire");
        LEA.put(306,"Croydon");
        LEA.put(383,"Leeds");
        LEA.put(909,"Cumbria");
        LEA.put(856,"Leicester");
        LEA.put(841,"Darlington");
        LEA.put(855,"Leicestershire");
        LEA.put(831,"Derby");
        LEA.put(209,"Lewisham");
        LEA.put(830,"Derbyshire");
        LEA.put(925,"Lincolnshire");
        LEA.put(878,"Devon");
        LEA.put(341,"Liverpool");
        LEA.put(371,"Doncaster");
        LEA.put(821,"Luton");
        LEA.put(835,"Dorset");
        LEA.put(352,"Manchester");
        LEA.put(332,"Dudley");
        LEA.put(887,"Medway");
        LEA.put(840,"Durham");
        LEA.put(315,"Merton");
        LEA.put(307,"Ealing");
        LEA.put(806,"Middlesbrough");
        LEA.put(811,"East Riding of Yorkshire");
        LEA.put(826,"Milton Keynes");
        LEA.put(845,"East Sussex");
        LEA.put(391,"Newcastle upon Tyne");
        LEA.put(316,"Newham");
        LEA.put(935,"Suffolk");
        LEA.put(926,"Norfolk");
        LEA.put(394,"Sunderland");
        LEA.put(812,"North East Lincolnshire");
        LEA.put(936,"Surrey");
        LEA.put(813,"North Lincolnshire");
        LEA.put(319,"Sutton");
        LEA.put(802,"North Somerset");
        LEA.put(866,"Swindon");
        LEA.put(392,"North Tyneside");
        LEA.put(357,"Tameside");
        LEA.put(815,"North Yorkshire");
        LEA.put(894,"Telford and Wrekin");
        LEA.put(928,"Northamptonshire");
        LEA.put(883,"Thurrock");
        LEA.put(929,"Northumberland");
        LEA.put(880,"Torbay");
        LEA.put(892,"Nottingham");
        LEA.put(211,"Tower Hamlets");
        LEA.put(891,"Nottinghamshire");
        LEA.put(358,"Trafford");
        LEA.put(353,"Oldham");
        LEA.put(384,"Wakefield");
        LEA.put(931,"Oxfordshire");
        LEA.put(335,"Walsall");
        LEA.put(874,"Peterborough");
        LEA.put(320,"Waltham Forest");
        LEA.put(879,"Plymouth");
        LEA.put(212,"Wandsworth");
        LEA.put(836,"Poole");
        LEA.put(877,"Warrington");
        LEA.put(851,"Portsmouth");
        LEA.put(937,"Warwickshire");
        LEA.put(870,"Reading");
        LEA.put(869,"West Berkshire");
        LEA.put(317,"Redbridge");
        LEA.put(938,"West Sussex");
        LEA.put(807,"Redcar and Cleveland");
        LEA.put(213,"Westminster");
        LEA.put(318,"Richmond upon Thames");
        LEA.put(359,"Wigan");
        LEA.put(354,"Rochdale");
        LEA.put(865,"Wiltshire");
        LEA.put(372,"Rotherham");
        LEA.put(868,"Windsor and Maidenhead");
        LEA.put(857,"Rutland");
        LEA.put(344,"Wirral");
        LEA.put(355,"Salford");
        LEA.put(872,"Wokingham");
        LEA.put(333,"Sandwell");
        LEA.put(336,"Wolverhampton");
        LEA.put(343,"Sefton");
        LEA.put(885,"Worcestershire");
        LEA.put(373,"Sheffield");
        LEA.put(816,"York");
        LEA.put(893,"Shropshire");
        LEA.put(702,"Service Children’s Education");
        LEA.put(871,"Slough");
        LEA.put(334,"Solihull");
        LEA.put(933,"Somerset");
        LEA.put(803,"South Gloucestershire");
        LEA.put(393,"South Tyneside");
        LEA.put(852,"Southampton");
        LEA.put(882,"Southend on Sea");
        LEA.put(210,"Southwark");
        LEA.put(342,"St Helens");
        LEA.put(860,"Staffordshire");
        LEA.put(356,"Stockport");
        LEA.put(808,"Stockton on Tees");
        LEA.put(861,"Stoke on Trent");
    }

    // NCCIS sets the format of dates in XML to be thus
    public static final String DATE_FORMAT = "yyyy-MM-dd";
}
