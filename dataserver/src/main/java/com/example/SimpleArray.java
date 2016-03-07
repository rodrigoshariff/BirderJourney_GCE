package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rmendoza on 3/4/2016.
 */
public class SimpleArray {

    static String[][] birds = new String[][]
    {
        {"22696776", "Double-crested Cormorant (Phalacrocorax auritus)", "Cormorants (Phalacrocoracidae)", "SULIFORMES", "The double-crested cormorant occurs along inland waterways as well as in coastal areas, measuring 70–90 cm (28–35 in) in length, it is an all-black bird which gains a small double crest of black and white feathers in breeding season.", "LEAST CONCERN (LC)", "i-ZZKBZkk"},
        {"22733989", "Brown Pelican (Pelecanus occidentalis)", "Pelicans (Pelecanidae)", "PELECANIFORMES", "The brown pelican is the smallest of the eight species of pelican, although it is a large bird in nearly every other regard. It is 106–137 cm (42–54 in) in length, weighs from 2.75 to 5.5 kg (6.1 to 12.1 lb) and has a wingspan from 1.83 to 2.5 m (6.0 to 8.2 ft). ", "LEAST CONCERN (LC)", "i-d8Q2KMn"},
        {"22696998", "Great Blue Heron (Ardea herodias)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The great blue heron is the largest North American heron and, among all extant herons, it is surpassed only by the goliath heron (Ardea goliath) and the white-bellied heron (Ardea insignis). It has head-to-tail length of 91–137 cm (36–54 in}, a wingspan of 167–201 cm (66–79 in}, a height of 115–138 cm (45–54 in}, and a weight of 1.82–3.6 kg (4.0–7.9 lb)", "LEAST CONCERN (LC)", "i-9z2JCPR"},
        {"22697043", "Great Egret (Ardea alba)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The great egret is a large, widely distributed egret. Distributed across most of the tropical and warmer temperate regions of the world. Standing up to 1 m (3.3 ft) tall, this species can measure 80 to 104 cm (31 to 41 in) in length and have a wingspan of 131 to 170 cm (52 to 67 in).[5][6] Body mass can range from 700 to 1,500 g (1.5 to 3.3 lb).", "LEAST CONCERN (LC)", "i-3rTJpnZ"},
        {"22696974", "Snowy Egret (Egretta thula)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The snowy egret is a small white heron. It is the American counterpart to the very similar Old World little egret, which has established a foothold in the Bahamas. Adults are typically 61 cm (24 in) long and weigh 375 g (0.827 lb) They have a slim black bill and long black legs with yellow feet.", "LEAST CONCERN (LC)", "i-jZn8qw9"},
        {"22696944", "Little Blue Heron (Egretta caerulea)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The little blue heron that breeds in the Gulf states of the US, through Central America and the Caribbean.  This species is about 60 cm (24 in) long, with a 102 cm (40 in) wingspan, and weighs 325 g (11.5 oz). ", "LEAST CONCERN (LC)", "i-bV68fQ9"},
        {"22696931", "Tricolored Heron (Egretta tricolor)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The tricolored heron is a small heron, resident breeder from the Gulf states of the USA and northern Mexico south through Central America and the Caribbean to central Brazil and Peru. This species measures from 56 to 76 cm (22 to 30 in) long and has a wingspan of 96 cm (38 in).The slightly larger male heron weighs 415 g (14.6 oz) on average, while the female averages 334 g (11.8 oz).", "LEAST CONCERN (LC)", "i-pjPKQmc"},
        {"22696916", "Reddish Egret (Egretta rufescens)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The reddish egret is a medium-sized heron resident breeder in Central America, The Bahamas, the Caribbean, the Gulf Coast of the United States, and Mexico. This species reaches 68–82 cm (27–32 in) in length, with a 116–125 cm (46–49 in) wingspan. Body mass in this species can range from 364–870 g (0.802–1.918 lb).", "NEAR THREATENED", "i-CSprjks"},
        {"22697109", "Cattle Egret (Bubulcus ibis)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The cattle egret is a cosmopolitan species of heron found in the tropics, subtropics and warm temperate zones. The cattle egret is a stocky heron with an 88–96 cm (35–38 in) wingspan; it is 46–56 cm (18–22 in) long and weighs 270–512 g (9.5–18.1 oz).", "LEAST CONCERN (LC)", "i-Zg7dhtc"},
        {"22697182", "Green Heron (Butorides virescens)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The green heron is a relatively small heron of North and Central America. Adult body length is about 44 cm (17 in).", "NOT RECOGNISED", "i-fbb7rhd"},
        {"22697211", "Black-crowned Night-Heron (Nycticorax nycticorax)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The black-crowned night heron, commonly abbreviated to just night heron in Eurasia, is a medium-sized heron found throughout a large part of the world, except in the coldest regions and Australasia.  This species measures approximately 64 cm (25 in) long and weigh 800 g (28 oz).", "LEAST CONCERN (LC)", "i-qJQBhqK"},
        {"22697203", "Yellow-crowned Night-Heron (Nyctanassa violacea)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The yellow-crowned night-heron is one of two species of night herons found in the Americas.   It is a rather stocky wading bird, ranging from 55 to 70 cm and from 650 to 800 grams, the females being a little smaller than the males. ", "LEAST CONCERN (LC)", "i-5JMp4Sv"},
        {"22697411", "White Ibis (Eudocimus albus)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The American white ibis is found from North Carolina via the Gulf Coast of the United States south through most of the coastal New World tropics. This particular ibis is a medium-sized bird with an overall white plumage, bright red-orange down-curved bill and long legs, and black wing tips that are usually only visible in flight.", "LEAST CONCERN (LC)", "i-g4zhZ38"},
        {"22697422", "Glossy Ibis (Plegadis falcinellus)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The Glossy ibis is the most widespread ibis species, breeding in scattered sites in warm regions of Europe, Asia, Africa, Australia, and the Atlantic and Caribbean regions of the Americas.   This species measures approximately 48–66 cm (19–26 in) long, averaging around 59.4 cm (23.4 in) with an 80–105 cm (31–41 in) wingspan and the body mass of this ibis can range from 485 to 970 g (1.069 to 2.138 lb).", "LEAST CONCERN (LC)", "i-pLN7Kn4"},
        {"22697574", "Roseate Spoonbill (Platalea ajaja)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The roseate spoonbill is a gregarious wading bird of the ibis and spoonbill family. It is a resident breeder in South America mostly east of the Andes, and in coastal regions of the Caribbean, Central America, Mexico, the Gulf Coast of the United States and on central Florida Atlantic coast.  This species measures approximately 71–86 cm (28–34 in) long, with a 120–133 cm (47–52 in) wingspan and a body mass of 1.2–1.8 kg (2.6–4.0 lb).", "LEAST CONCERN (LC)", "i-V6f6cSN"},
        {"22697624", "Black Vulture (Coragyps atratus)", "New World Vultures (Cathartidae)", "CATHARTIFORMES", "The black vulture also known as the American black vulture, is a bird in the New World vulture family whose range extends from the southeastern United States to Central Chile and Uruguay in South America.  This species measures approximately 56–74 cm (22–29 in) in length, with a 1.33–1.67 m (52–66 in) wingspan. Weight for Black Vultures from North America and the Andes ranges from 1.6–2.75 kg (3.5–6.1 lb).", "LEAST CONCERN (LC)", "i-ZsFZqmm"},
        {"22697627", "Turkey Vulture (Cathartes aura)", "New World Vultures (Cathartidae)", "CATHARTIFORMES", "The turkey vulture, also known as the turkey buzzard is a vulture that is the most widespread of the New World vultures.  The turkey vulture ranges from southern Canada to the southernmost tip of South America.   This species has a wingspan of 160–183 cm (63–72 in}, a length of 62–81 cm (24–32 in}, and weight of 0.8 to 2.3 kg (1.8 to 5.1 lb).", "LEAST CONCERN (LC)", "i-T2HcvNs"},
        {"22694938", "Osprey (Pandion haliaetus)", "Ospreys (Pandionidae)", "ACCIPITRIFORMES", "", "LEAST CONCERN (LC)", "i-FmM7VMC"},
        {"22695048", "Snail Kite (Rostrhamus sociabilis)", "Hawks, Kites, Eagles, and Allies (Accipitridae)", "ACCIPITRIFORMES", "", "LEAST CONCERN (LC)", "i-58ptnhq"},
        {"22692078", "Sandhill Crane (Grus canadensis)", "Cranes (Gruidae)", "GRUIFORMES", "", "LEAST CONCERN (LC)", "i-3v9XjCH"},
        {"22693764", "Semipalmated Plover (Charadrius semipalmatus)", "Lapwings and Plovers (Charadriidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-6gRGMVK"},
        {"22693811", "Piping Plover (Charadrius melodus)", "Lapwings and Plovers (Charadriidae)", "CHARADRIIFORMES", "", "NEAR THREATENED", "i-wc29SCB"},
        {"22693363", "Red Knot (Calidris canutus)", "Sandpipers, Phalaropes, and Allies (Scolopacidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-wRrqs6d"},
        {"22693369", "Sanderling (Calidris alba)", "Sandpipers, Phalaropes, and Allies (Scolopacidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-84QLvtZ"},
        {"22694542", "Royal Tern (Thalasseus maximus)", "Gulls, Terns, and Skimmers (Laridae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-WC5Mh3T"},
        {"22694256", "Black Skimmer (Rynchops niger)", "Gulls, Terns, and Skimmers (Laridae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-vLTXJd5"},
        {"22689353", "Burrowing Owl (Athene cunicularia)", "Typical Owls (Strigidae)", "STRIGIFORMES", "", "LEAST CONCERN (LC)", "i-RnNpNcH"},
        {"22726404", "Northern Flicker (Colaptes auratus)", "Woodpeckers and Allies (Picidae)", "PICIFORMES", "", "LEAST CONCERN (LC)", "i-NnJMZk5"},
        {"22699816", "Eastern Wood-Pewee (Contopus virens)", "Tyrant Flycatchers (Tyrannidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-m4r7wMr"},
        {"22705042", "Loggerhead Shrike (Lanius ludovicianus)", "Shrikes (Laniidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-QDSSSDH"},
        {"22705611", "Blue Jay (Cyanocitta cristata)", "Jays and Crows (Corvidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-cRPcC7Z"},
        {"22711581", "Blue-gray Gnatcatcher (Polioptila caerulea)", "Gnatcatchers and Gnatwrens (Polioptilidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-Q9czpBK"},
        {"22710921", "Common Myna (Acridotheres tristis)", "Starlings (Sturnidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-GkLdxfM"},
        {"22721836", "Common Yellowthroat (Geothlypis trichas)", "Wood-Warblers (Parulidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-hzWdgWH"},
        {"22735434", "Eastern Meadowlark (Sturnella magna)", "Blackbirds (Icteridae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-xNgdQDb"},
        {"22724311", "Boat-tailed Grackle (Quiscalus major)", "Blackbirds (Icteridae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-RPNfgZt"}
    };

    public void loadArray() {
        String[][] animals = {
                {"22696776", "Double-crested Cormorant (Phalacrocorax auritus)", "Cormorants (Phalacrocoracidae)", "SULIFORMES", "The double-crested cormorant occurs along inland waterways as well as in coastal areas, measuring 70–90 cm (28–35 in) in length, it is an all-black bird which gains a small double crest of black and white feathers in breeding season.", "LEAST CONCERN (LC)", "i-ZZKBZkk"},
                {"22733989", "Brown Pelican (Pelecanus occidentalis)", "Pelicans (Pelecanidae)", "PELECANIFORMES", "The brown pelican is the smallest of the eight species of pelican, although it is a large bird in nearly every other regard. It is 106–137 cm (42–54 in) in length, weighs from 2.75 to 5.5 kg (6.1 to 12.1 lb) and has a wingspan from 1.83 to 2.5 m (6.0 to 8.2 ft). ", "LEAST CONCERN (LC)", "i-d8Q2KMn"},
                {"22696998", "Great Blue Heron (Ardea herodias)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The great blue heron is the largest North American heron and, among all extant herons, it is surpassed only by the goliath heron (Ardea goliath) and the white-bellied heron (Ardea insignis). It has head-to-tail length of 91–137 cm (36–54 in}, a wingspan of 167–201 cm (66–79 in}, a height of 115–138 cm (45–54 in}, and a weight of 1.82–3.6 kg (4.0–7.9 lb)", "LEAST CONCERN (LC)", "i-9z2JCPR"},
                {"22697043", "Great Egret (Ardea alba)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The great egret is a large, widely distributed egret. Distributed across most of the tropical and warmer temperate regions of the world. Standing up to 1 m (3.3 ft) tall, this species can measure 80 to 104 cm (31 to 41 in) in length and have a wingspan of 131 to 170 cm (52 to 67 in).[5][6] Body mass can range from 700 to 1,500 g (1.5 to 3.3 lb).", "LEAST CONCERN (LC)", "i-3rTJpnZ"},
                {"22696974", "Snowy Egret (Egretta thula)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The snowy egret is a small white heron. It is the American counterpart to the very similar Old World little egret, which has established a foothold in the Bahamas. Adults are typically 61 cm (24 in) long and weigh 375 g (0.827 lb) They have a slim black bill and long black legs with yellow feet.", "LEAST CONCERN (LC)", "i-jZn8qw9"},
                {"22696944", "Little Blue Heron (Egretta caerulea)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The little blue heron that breeds in the Gulf states of the US, through Central America and the Caribbean.  This species is about 60 cm (24 in) long, with a 102 cm (40 in) wingspan, and weighs 325 g (11.5 oz). ", "LEAST CONCERN (LC)", "i-bV68fQ9"},
                {"22696931", "Tricolored Heron (Egretta tricolor)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The tricolored heron is a small heron, resident breeder from the Gulf states of the USA and northern Mexico south through Central America and the Caribbean to central Brazil and Peru. This species measures from 56 to 76 cm (22 to 30 in) long and has a wingspan of 96 cm (38 in).The slightly larger male heron weighs 415 g (14.6 oz) on average, while the female averages 334 g (11.8 oz).", "LEAST CONCERN (LC)", "i-pjPKQmc"},
                {"22696916", "Reddish Egret (Egretta rufescens)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The reddish egret is a medium-sized heron resident breeder in Central America, The Bahamas, the Caribbean, the Gulf Coast of the United States, and Mexico. This species reaches 68–82 cm (27–32 in) in length, with a 116–125 cm (46–49 in) wingspan. Body mass in this species can range from 364–870 g (0.802–1.918 lb).", "NEAR THREATENED", "i-CSprjks"},
                {"22697109", "Cattle Egret (Bubulcus ibis)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The cattle egret is a cosmopolitan species of heron found in the tropics, subtropics and warm temperate zones. The cattle egret is a stocky heron with an 88–96 cm (35–38 in) wingspan; it is 46–56 cm (18–22 in) long and weighs 270–512 g (9.5–18.1 oz).", "LEAST CONCERN (LC)", "i-Zg7dhtc"},
                {"22697182", "Green Heron (Butorides virescens)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The green heron is a relatively small heron of North and Central America. Adult body length is about 44 cm (17 in).", "NOT RECOGNISED", "i-fbb7rhd"},
                {"22697211", "Black-crowned Night-Heron (Nycticorax nycticorax)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The black-crowned night heron, commonly abbreviated to just night heron in Eurasia, is a medium-sized heron found throughout a large part of the world, except in the coldest regions and Australasia.  This species measures approximately 64 cm (25 in) long and weigh 800 g (28 oz).", "LEAST CONCERN (LC)", "i-qJQBhqK"},
                {"22697203", "Yellow-crowned Night-Heron (Nyctanassa violacea)", "Bitterns, Herons, and Allies (Ardeidae)", "PELECANIFORMES", "The yellow-crowned night-heron is one of two species of night herons found in the Americas.   It is a rather stocky wading bird, ranging from 55 to 70 cm and from 650 to 800 grams, the females being a little smaller than the males. ", "LEAST CONCERN (LC)", "i-5JMp4Sv"},
                {"22697411", "White Ibis (Eudocimus albus)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The American white ibis is found from North Carolina via the Gulf Coast of the United States south through most of the coastal New World tropics. This particular ibis is a medium-sized bird with an overall white plumage, bright red-orange down-curved bill and long legs, and black wing tips that are usually only visible in flight.", "LEAST CONCERN (LC)", "i-g4zhZ38"},
                {"22697422", "Glossy Ibis (Plegadis falcinellus)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The Glossy ibis is the most widespread ibis species, breeding in scattered sites in warm regions of Europe, Asia, Africa, Australia, and the Atlantic and Caribbean regions of the Americas.   This species measures approximately 48–66 cm (19–26 in) long, averaging around 59.4 cm (23.4 in) with an 80–105 cm (31–41 in) wingspan and the body mass of this ibis can range from 485 to 970 g (1.069 to 2.138 lb).", "LEAST CONCERN (LC)", "i-pLN7Kn4"},
                {"22697574", "Roseate Spoonbill (Platalea ajaja)", "Ibises and Spoonbills (Threskiornithidae)", "PELECANIFORMES", "The roseate spoonbill is a gregarious wading bird of the ibis and spoonbill family. It is a resident breeder in South America mostly east of the Andes, and in coastal regions of the Caribbean, Central America, Mexico, the Gulf Coast of the United States and on central Florida Atlantic coast.  This species measures approximately 71–86 cm (28–34 in) long, with a 120–133 cm (47–52 in) wingspan and a body mass of 1.2–1.8 kg (2.6–4.0 lb).", "LEAST CONCERN (LC)", "i-V6f6cSN"},
                {"22697624", "Black Vulture (Coragyps atratus)", "New World Vultures (Cathartidae)", "CATHARTIFORMES", "The black vulture also known as the American black vulture, is a bird in the New World vulture family whose range extends from the southeastern United States to Central Chile and Uruguay in South America.  This species measures approximately 56–74 cm (22–29 in) in length, with a 1.33–1.67 m (52–66 in) wingspan. Weight for Black Vultures from North America and the Andes ranges from 1.6–2.75 kg (3.5–6.1 lb).", "LEAST CONCERN (LC)", "i-ZsFZqmm"},
                {"22697627", "Turkey Vulture (Cathartes aura)", "New World Vultures (Cathartidae)", "CATHARTIFORMES", "The turkey vulture, also known as the turkey buzzard is a vulture that is the most widespread of the New World vultures.  The turkey vulture ranges from southern Canada to the southernmost tip of South America.   This species has a wingspan of 160–183 cm (63–72 in}, a length of 62–81 cm (24–32 in}, and weight of 0.8 to 2.3 kg (1.8 to 5.1 lb).", "LEAST CONCERN (LC)", "i-T2HcvNs"},
                {"22694938", "Osprey (Pandion haliaetus)", "Ospreys (Pandionidae)", "ACCIPITRIFORMES", "", "LEAST CONCERN (LC)", "i-FmM7VMC"},
                {"22695048", "Snail Kite (Rostrhamus sociabilis)", "Hawks, Kites, Eagles, and Allies (Accipitridae)", "ACCIPITRIFORMES", "", "LEAST CONCERN (LC)", "i-58ptnhq"},
                {"22692078", "Sandhill Crane (Grus canadensis)", "Cranes (Gruidae)", "GRUIFORMES", "", "LEAST CONCERN (LC)", "i-3v9XjCH"},
                {"22693764", "Semipalmated Plover (Charadrius semipalmatus)", "Lapwings and Plovers (Charadriidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-6gRGMVK"},
                {"22693811", "Piping Plover (Charadrius melodus)", "Lapwings and Plovers (Charadriidae)", "CHARADRIIFORMES", "", "NEAR THREATENED", "i-wc29SCB"},
                {"22693363", "Red Knot (Calidris canutus)", "Sandpipers, Phalaropes, and Allies (Scolopacidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-wRrqs6d"},
                {"22693369", "Sanderling (Calidris alba)", "Sandpipers, Phalaropes, and Allies (Scolopacidae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-84QLvtZ"},
                {"22694542", "Royal Tern (Thalasseus maximus)", "Gulls, Terns, and Skimmers (Laridae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-WC5Mh3T"},
                {"22694256", "Black Skimmer (Rynchops niger)", "Gulls, Terns, and Skimmers (Laridae)", "CHARADRIIFORMES", "", "LEAST CONCERN (LC)", "i-vLTXJd5"},
                {"22689353", "Burrowing Owl (Athene cunicularia)", "Typical Owls (Strigidae)", "STRIGIFORMES", "", "LEAST CONCERN (LC)", "i-RnNpNcH"},
                {"22726404", "Northern Flicker (Colaptes auratus)", "Woodpeckers and Allies (Picidae)", "PICIFORMES", "", "LEAST CONCERN (LC)", "i-NnJMZk5"},
                {"22699816", "Eastern Wood-Pewee (Contopus virens)", "Tyrant Flycatchers (Tyrannidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-m4r7wMr"},
                {"22705042", "Loggerhead Shrike (Lanius ludovicianus)", "Shrikes (Laniidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-QDSSSDH"},
                {"22705611", "Blue Jay (Cyanocitta cristata)", "Jays and Crows (Corvidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-cRPcC7Z"},
                {"22711581", "Blue-gray Gnatcatcher (Polioptila caerulea)", "Gnatcatchers and Gnatwrens (Polioptilidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-Q9czpBK"},
                {"22710921", "Common Myna (Acridotheres tristis)", "Starlings (Sturnidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-GkLdxfM"},
                {"22721836", "Common Yellowthroat (Geothlypis trichas)", "Wood-Warblers (Parulidae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-hzWdgWH"},
                {"22735434", "Eastern Meadowlark (Sturnella magna)", "Blackbirds (Icteridae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-xNgdQDb"},
                {"22724311", "Boat-tailed Grackle (Quiscalus major)", "Blackbirds (Icteridae)", "PASSERIFORMES", "", "LEAST CONCERN (LC)", "i-RPNfgZt"}
        };

        birds = animals;
    }

    public ArrayList<String> getSearchedBirds(String searchstring) {

        ArrayList<String> array_list = new ArrayList<String>();


        for (int i = 0; i < birds.length; i++) {
            if (birds[i][1].toLowerCase().contains(searchstring.toLowerCase())) {
                array_list.add(birds[i][1]);
            }
        }
        return array_list;
    }



}


