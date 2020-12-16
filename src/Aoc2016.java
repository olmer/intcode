import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aoc2016 {
    public static void main(String[] args) {
        var raw = ("departure location: 31-201 or 227-951\n" +
            "departure station: 49-885 or 892-961\n" +
            "departure platform: 36-248 or 258-974\n" +
            "departure track: 37-507 or 527-965\n" +
            "departure date: 37-331 or 351-970\n" +
            "departure time: 38-370 or 382-970\n" +
            "arrival location: 33-686 or 711-960\n" +
            "arrival station: 46-753 or 775-953\n" +
            "arrival platform: 34-138 or 154-959\n" +
            "arrival track: 26-167 or 181-961\n" +
            "class: 43-664 or 675-968\n" +
            "duration: 47-603 or 620-954\n" +
            "price: 40-290 or 313-972\n" +
            "route: 37-792 or 799-972\n" +
            "row: 32-97 or 115-954\n" +
            "seat: 25-916 or 942-966\n" +
            "train: 39-572 or 587-966\n" +
            "type: 25-834 or 858-953\n" +
            "wagon: 48-534 or 544-959\n" +
            "zone: 47-442 or 463-969\n" +
            "\n" +
            "your ticket:\n" +
            "127,83,79,197,157,67,71,131,97,193,181,191,163,61,53,89,59,137,73,167\n" +
            "\n" +
            "nearby tickets:\n" +
            "196,877,117,24,56,120,416,948,677,238,628,285,326,813,155,659,91,717,287,279\n" +
            "354,870,624,158,473,361,565,896,873,549,740,714,380,827,880,871,271,187,66,662\n" +
            "563,659,90,894,686,496,245,500,188,426,492,127,428,166,473,430,278,619,395,587\n" +
            "533,833,95,385,17,397,263,480,386,318,725,567,127,50,353,82,238,86,368,787\n" +
            "884,682,480,389,784,118,50,713,566,119,163,707,816,711,357,623,498,638,657,195\n" +
            "529,132,427,470,569,473,277,271,622,432,227,66,818,829,135,616,56,652,392,657\n" +
            "746,184,188,352,197,157,505,560,622,598,482,603,883,858,867,601,348,439,648,742\n" +
            "812,884,737,809,801,595,678,390,814,811,731,53,712,435,724,166,129,539,751,804\n" +
            "620,656,482,403,156,78,814,543,485,65,743,656,242,783,89,820,81,675,280,570\n" +
            "626,633,54,783,712,588,239,400,81,627,421,492,493,81,4,495,355,896,811,51\n" +
            "467,643,495,77,4,399,911,233,235,123,621,801,127,288,810,743,501,283,61,914\n" +
            "86,788,88,863,412,88,558,494,259,269,185,484,652,60,626,161,676,426,126,613\n" +
            "645,819,470,186,234,331,182,826,326,533,165,171,441,830,894,641,547,658,819,565\n" +
            "682,409,630,465,881,268,738,942,682,239,84,802,466,428,722,535,426,81,354,183\n" +
            "820,323,425,329,628,62,660,658,752,791,734,814,595,363,652,820,891,288,196,497\n" +
            "716,798,188,286,433,232,566,896,736,663,264,775,201,408,949,830,424,227,711,495\n" +
            "432,244,592,262,64,89,85,278,256,96,187,946,635,554,55,503,807,465,493,434\n" +
            "419,502,125,196,638,789,479,352,233,630,335,734,128,479,862,157,662,883,872,442\n" +
            "874,62,430,598,535,640,116,200,602,442,53,126,803,647,327,566,464,390,787,262\n" +
            "441,491,423,779,310,871,563,137,632,788,433,246,834,720,629,131,363,898,529,913\n" +
            "557,894,567,416,313,777,464,877,603,90,827,657,283,385,384,946,171,481,197,732\n" +
            "629,602,267,403,85,135,558,154,132,273,824,659,116,420,798,647,189,351,807,188\n" +
            "88,94,493,248,801,532,827,546,869,274,836,237,489,678,746,60,238,440,637,903\n" +
            "735,439,659,154,830,547,632,720,718,561,354,812,719,868,264,925,429,897,485,284\n" +
            "85,549,257,567,893,124,56,356,677,137,167,639,659,913,893,415,529,331,602,360\n" +
            "733,552,227,284,469,115,556,479,268,536,404,711,735,406,404,883,229,194,397,135\n" +
            "137,497,780,468,164,801,137,489,634,76,427,525,748,911,244,121,866,392,679,602\n" +
            "96,622,810,736,188,330,589,720,626,614,391,388,948,95,884,274,879,569,832,396\n" +
            "495,891,882,482,66,85,442,264,900,193,729,275,56,157,637,598,281,908,434,64\n" +
            "946,744,502,736,913,229,832,629,368,501,648,341,650,829,370,489,75,904,407,640\n" +
            "126,785,630,567,891,388,195,76,727,658,734,405,859,322,645,947,723,50,908,232\n" +
            "181,128,406,134,598,174,877,167,875,351,472,722,662,361,910,711,714,478,563,908\n" +
            "778,631,362,486,625,164,470,425,560,900,734,57,393,545,822,173,659,183,354,438\n" +
            "644,812,413,468,815,730,944,654,553,252,894,596,59,433,119,479,629,85,156,441\n" +
            "357,477,898,158,730,905,945,824,315,313,804,828,51,231,420,638,360,979,570,403\n" +
            "720,286,646,246,600,787,270,96,162,911,412,557,187,502,879,897,717,854,80,424\n" +
            "326,897,510,478,370,903,159,528,192,730,561,821,742,869,285,548,686,649,658,277\n" +
            "51,607,87,858,872,652,531,553,811,652,907,570,392,280,266,231,949,553,402,323\n" +
            "472,657,429,882,327,906,7,832,326,945,663,904,416,572,629,86,437,398,743,84\n" +
            "550,542,190,406,715,288,534,116,914,635,195,320,883,227,564,164,572,191,601,119\n" +
            "788,878,283,399,115,809,112,904,504,265,630,787,596,505,915,653,817,783,85,351\n" +
            "413,744,620,802,91,136,87,364,945,433,57,54,628,823,633,413,993,388,601,274\n" +
            "645,819,431,811,78,88,484,596,283,856,531,506,138,893,385,78,369,241,639,595\n" +
            "496,156,83,533,714,347,229,128,633,184,418,289,279,913,87,387,330,563,572,730\n" +
            "815,818,798,596,352,810,867,246,386,90,867,620,775,266,318,801,328,833,783,329\n" +
            "474,413,95,191,122,255,625,653,417,745,233,554,916,485,200,118,482,907,644,824\n" +
            "122,854,388,874,400,636,406,187,420,571,135,90,597,359,58,791,777,505,187,246\n" +
            "265,126,811,197,749,634,62,787,190,68,165,779,413,519,569,52,557,791,503,867\n" +
            "862,66,290,96,93,465,603,562,589,714,537,61,473,426,675,392,369,432,383,807\n" +
            "248,123,642,905,241,825,278,259,862,486,432,355,743,822,304,128,351,121,663,800\n" +
            "117,137,484,713,413,76,143,569,646,728,894,279,490,600,777,806,91,116,803,725\n" +
            "750,546,466,133,825,898,297,236,121,132,275,167,822,900,628,789,828,946,239,832\n" +
            "864,636,165,124,563,549,155,868,538,388,438,247,248,316,729,366,663,82,730,494\n" +
            "124,720,649,358,199,84,663,274,490,885,631,371,480,420,567,812,195,280,72,834\n" +
            "399,417,814,289,120,61,189,382,70,718,630,713,840,556,129,801,821,315,743,554\n" +
            "864,500,121,734,830,782,823,775,603,461,750,738,868,908,504,126,911,63,645,602\n" +
            "812,273,421,654,228,69,163,642,592,376,621,278,79,572,652,138,630,165,247,534\n" +
            "419,862,175,387,861,160,478,685,401,440,352,362,164,791,897,198,95,133,131,561\n" +
            "527,944,410,626,587,329,324,949,818,547,763,590,325,259,56,230,790,266,66,864\n" +
            "816,272,498,560,563,140,864,82,659,720,158,718,631,916,810,160,133,569,599,602\n" +
            "118,195,891,79,390,815,712,813,358,476,807,467,483,200,533,596,783,873,191,420\n" +
            "163,552,683,561,685,77,947,624,623,260,249,902,914,409,239,137,894,466,803,805\n" +
            "384,911,810,481,248,415,727,62,135,882,815,615,945,479,195,135,74,329,652,232\n" +
            "287,239,118,554,649,97,427,655,308,721,314,740,283,417,554,473,427,913,570,907\n" +
            "127,873,719,386,781,840,945,322,370,483,437,915,280,265,245,72,719,829,629,385\n" +
            "725,92,355,782,556,803,201,909,184,596,130,430,403,714,274,273,882,528,135,0\n" +
            "664,384,342,869,883,907,658,90,370,409,115,729,389,130,490,900,716,431,867,269\n" +
            "487,592,482,235,19,529,76,167,259,245,245,57,237,186,601,315,723,239,489,281\n" +
            "165,819,648,438,290,372,411,720,860,914,779,751,676,195,753,717,158,812,126,751\n" +
            "991,243,810,747,92,553,828,73,595,679,589,547,747,625,897,442,165,904,69,67\n" +
            "133,747,167,537,167,94,490,831,90,944,438,356,82,743,822,675,949,392,949,159\n" +
            "877,313,284,360,812,788,126,621,201,438,11,314,427,719,314,71,385,870,384,258\n" +
            "494,56,77,284,636,810,385,653,732,170,884,571,185,63,947,423,198,716,405,529\n" +
            "89,907,779,728,641,484,753,184,981,864,740,58,650,86,635,463,876,121,601,547\n" +
            "317,548,248,186,359,391,890,275,878,825,779,786,247,330,803,135,323,864,120,790\n" +
            "240,407,603,826,785,877,790,163,342,391,753,714,420,396,94,391,714,899,473,165\n" +
            "396,874,199,10,639,268,719,830,738,726,489,647,50,626,571,488,902,733,718,629\n" +
            "654,407,438,880,414,382,882,127,56,679,463,629,589,305,315,625,281,780,946,362\n" +
            "195,362,799,96,915,133,225,486,354,489,815,122,867,625,94,679,913,237,880,159\n" +
            "572,328,556,258,745,134,831,492,469,778,486,534,331,978,832,745,593,421,157,82\n" +
            "595,567,67,360,866,939,404,650,164,362,328,504,880,54,716,646,404,286,897,394\n" +
            "734,315,430,50,786,263,747,475,55,915,888,531,792,320,494,602,807,740,903,121\n" +
            "463,808,728,806,547,400,232,227,791,827,942,156,259,725,568,789,441,572,7,154\n" +
            "389,116,96,834,116,439,470,635,609,811,639,159,914,862,69,678,572,568,390,126\n" +
            "730,753,616,653,553,752,382,132,686,644,751,408,183,95,591,804,158,394,116,712\n" +
            "533,863,329,551,529,651,184,68,911,726,408,192,727,402,273,343,620,597,558,358\n" +
            "601,417,87,639,464,534,755,66,777,623,558,229,655,52,232,422,190,55,231,93\n" +
            "555,471,415,437,52,529,259,477,427,500,865,833,539,135,81,589,492,501,874,51\n" +
            "881,739,317,505,860,682,125,408,945,944,502,327,137,159,388,912,768,50,117,507\n" +
            "56,552,567,51,645,370,780,601,745,126,85,682,533,394,527,897,466,996,485,906\n" +
            "485,748,315,735,544,910,821,872,165,506,398,537,281,725,76,331,621,821,864,571\n" +
            "802,274,487,867,624,404,235,901,905,530,129,988,498,632,389,80,64,154,426,196\n" +
            "363,414,591,194,61,323,392,779,358,212,229,200,679,417,358,319,503,59,92,54\n" +
            "421,492,897,77,562,83,526,877,594,242,265,500,441,859,155,830,817,872,500,785\n" +
            "435,118,442,440,548,752,561,820,284,74,260,491,736,63,427,758,534,799,80,821\n" +
            "628,724,260,480,913,882,406,799,183,634,751,797,651,649,825,465,361,785,393,862\n" +
            "420,663,137,828,258,478,386,365,154,724,907,238,795,91,426,567,388,877,51,489\n" +
            "881,130,650,313,624,647,130,714,426,64,905,821,553,732,436,623,905,222,441,325\n" +
            "741,807,396,241,943,742,265,794,553,421,814,125,393,322,828,271,781,493,587,947\n" +
            "426,93,645,946,125,736,618,420,822,719,682,473,546,399,528,232,621,316,681,154\n" +
            "90,751,791,436,805,664,857,822,244,157,315,193,721,399,317,713,155,676,76,680\n" +
            "811,368,268,394,600,909,919,830,635,717,259,901,73,799,395,134,644,658,282,819\n" +
            "601,785,65,120,271,750,591,292,63,403,884,94,273,663,54,247,792,474,399,643\n" +
            "564,85,133,552,799,889,897,716,50,713,811,272,488,567,893,383,421,97,408,383\n" +
            "588,567,81,77,128,630,553,649,800,631,417,195,314,426,892,391,798,84,915,467\n" +
            "726,646,820,904,76,232,869,646,654,660,877,657,404,558,278,534,256,54,717,427\n" +
            "747,864,564,67,781,596,57,678,736,343,910,353,405,426,260,439,90,823,529,467\n" +
            "422,639,738,684,481,263,130,92,131,640,81,110,243,490,471,735,136,51,637,482\n" +
            "439,83,722,125,431,231,248,833,683,631,976,905,240,786,908,468,906,116,116,815\n" +
            "196,66,50,652,803,183,945,783,864,412,398,401,342,494,436,273,678,81,427,734\n" +
            "424,629,820,15,559,656,781,597,475,259,553,115,869,601,126,912,287,405,717,464\n" +
            "428,72,559,752,565,474,319,805,90,351,506,326,603,981,898,527,657,712,560,286\n" +
            "663,66,792,241,568,556,591,603,904,323,484,833,276,338,59,236,947,864,943,436\n" +
            "434,545,400,68,624,895,903,633,315,905,864,87,547,569,876,891,642,234,789,95\n" +
            "572,230,319,69,246,397,364,467,410,887,248,821,632,137,130,658,402,727,898,417\n" +
            "827,556,912,814,278,743,159,592,185,412,738,129,312,595,528,391,361,465,119,626\n" +
            "419,183,413,566,636,511,288,79,649,782,651,744,390,915,596,562,748,480,259,505\n" +
            "93,59,135,244,786,742,445,280,246,497,194,418,561,428,737,907,895,644,550,278\n" +
            "244,402,394,409,549,899,664,497,200,428,915,744,267,646,361,79,844,506,683,159\n" +
            "825,383,717,617,82,290,544,880,289,733,556,678,248,427,588,900,272,828,383,916\n" +
            "266,96,548,119,438,402,284,263,735,663,80,227,280,193,486,474,425,796,181,136\n" +
            "415,51,154,116,831,468,746,65,240,385,644,247,323,611,620,631,899,876,656,715\n" +
            "630,883,331,568,742,356,154,422,95,283,2,527,57,281,228,423,650,740,61,426\n" +
            "426,553,895,185,872,792,425,418,804,424,321,244,227,620,534,700,742,182,464,64\n" +
            "790,313,246,653,360,454,157,290,87,352,729,394,641,183,192,901,863,471,644,386\n" +
            "433,832,630,782,723,943,602,813,90,199,316,414,408,70,191,471,542,779,231,82\n" +
            "61,534,654,124,533,547,186,201,892,432,228,632,588,683,529,102,364,136,715,587\n" +
            "279,161,471,641,50,431,96,181,851,362,387,409,360,815,356,625,914,624,437,274\n" +
            "428,387,245,945,495,214,354,121,661,285,908,530,544,472,711,413,260,620,467,731\n" +
            "786,163,246,131,1,870,94,563,675,643,118,898,596,911,912,274,900,662,234,874\n" +
            "244,54,597,318,243,571,564,903,787,185,643,682,675,559,463,67,986,368,480,324\n" +
            "470,946,570,644,825,640,784,194,646,546,320,904,436,729,232,58,618,424,279,318\n" +
            "382,660,57,273,358,84,821,283,423,881,628,258,320,84,657,233,745,887,725,358\n" +
            "653,365,865,183,267,415,589,186,899,258,793,661,638,353,273,416,90,163,728,571\n" +
            "905,241,263,650,368,560,622,68,58,531,752,906,891,366,501,881,229,125,277,598\n" +
            "263,290,246,739,84,541,352,717,644,96,881,653,125,814,365,271,813,198,572,633\n" +
            "157,242,480,184,58,629,830,23,91,403,391,229,909,682,571,902,396,724,368,506\n" +
            "91,752,656,163,807,870,268,660,635,815,651,232,232,793,942,560,404,635,429,664\n" +
            "429,241,87,50,499,716,122,650,199,562,711,251,260,77,429,736,631,135,156,411\n" +
            "188,409,600,404,321,729,279,792,372,157,63,948,740,684,156,832,712,858,913,871\n" +
            "52,529,422,602,385,645,412,91,519,715,787,237,902,529,873,73,186,808,126,636\n" +
            "865,821,747,71,129,229,345,281,779,416,862,408,121,86,439,387,811,368,329,360\n" +
            "320,946,91,996,424,813,567,600,422,802,404,677,549,534,810,72,54,411,898,727\n" +
            "61,197,121,714,809,19,318,317,862,727,116,792,561,115,505,321,265,88,128,394\n" +
            "600,832,133,720,465,392,327,396,789,196,503,65,729,714,996,814,352,158,475,79\n" +
            "866,631,358,244,863,261,786,405,132,83,718,313,553,623,192,624,374,658,741,161\n" +
            "314,790,441,628,878,875,177,752,192,717,122,721,882,600,503,631,751,651,282,822\n" +
            "228,414,908,436,316,105,560,313,648,474,895,271,392,80,368,259,914,742,871,942\n" +
            "732,231,911,265,679,363,807,117,481,765,745,800,749,56,423,192,479,125,819,792\n" +
            "570,552,867,812,560,310,405,436,118,815,61,812,160,475,281,167,161,880,566,487\n" +
            "161,87,628,661,662,863,698,52,73,117,401,351,782,603,726,488,479,502,327,896\n" +
            "365,90,877,810,916,621,56,815,232,883,463,858,136,119,401,879,645,157,945,996\n" +
            "463,559,503,387,587,383,331,275,620,735,621,655,720,892,264,309,859,746,71,314\n" +
            "324,948,465,267,817,718,88,399,328,323,697,507,868,711,440,550,876,807,483,911\n" +
            "679,652,779,193,497,51,562,713,916,321,76,97,685,642,913,622,236,307,187,239\n" +
            "751,814,897,401,791,9,633,95,711,409,154,96,80,157,76,910,492,551,64,904\n" +
            "351,384,864,655,500,777,905,594,479,487,74,412,117,419,79,252,729,410,260,679\n" +
            "505,499,626,426,94,118,80,233,269,284,196,192,280,852,825,192,634,495,329,268\n" +
            "277,58,285,386,843,740,289,657,642,546,77,275,472,260,735,720,559,468,131,505\n" +
            "505,534,864,903,738,674,494,600,904,753,56,634,722,279,572,677,78,566,572,287\n" +
            "725,468,474,233,944,627,664,648,646,556,803,812,831,415,678,192,295,734,487,646\n" +
            "528,571,752,465,642,772,265,725,748,268,259,868,715,69,370,721,602,913,546,383\n" +
            "621,414,684,246,784,426,438,740,163,900,905,125,127,4,871,242,735,132,68,901\n" +
            "901,589,559,729,274,418,427,130,532,740,561,713,14,133,680,733,885,394,282,73\n" +
            "868,465,541,589,365,275,570,419,94,734,135,556,904,804,867,531,285,162,569,85\n" +
            "867,746,156,895,406,280,570,432,891,189,480,860,744,196,824,906,677,892,566,830\n" +
            "569,489,727,501,896,356,833,429,638,775,366,156,240,236,121,550,597,346,235,280\n" +
            "362,406,656,718,800,431,549,724,905,432,51,621,494,82,58,847,490,83,405,234\n" +
            "489,389,367,985,365,945,248,274,181,549,258,788,199,287,83,570,126,96,315,63\n" +
            "720,803,904,368,197,467,786,358,386,945,373,313,402,787,259,233,830,465,410,640\n" +
            "736,724,847,638,79,662,563,125,827,622,682,728,563,676,801,555,421,567,475,267\n" +
            "277,85,720,741,481,798,567,643,639,75,162,243,546,259,133,659,53,859,436,903\n" +
            "978,712,389,639,733,785,473,193,942,414,483,261,163,633,598,325,561,506,685,529\n" +
            "899,496,804,650,714,83,557,431,322,75,529,765,495,659,786,741,947,790,439,783\n" +
            "828,601,504,123,236,232,804,60,864,915,396,977,833,329,830,820,910,437,275,498\n" +
            "53,383,811,876,718,720,260,400,268,184,517,659,95,624,367,559,946,753,596,227\n" +
            "81,50,228,790,633,163,464,589,632,622,734,323,596,550,603,474,729,167,77,538\n" +
            "864,602,711,352,440,483,943,799,812,270,370,504,885,555,358,798,285,238,385,182\n" +
            "85,742,732,902,484,696,727,315,789,571,643,87,472,117,501,79,644,499,632,870\n" +
            "82,590,56,718,323,913,244,903,790,656,481,225,278,75,725,506,120,57,478,231\n" +
            "115,638,75,600,600,398,491,54,391,191,52,419,164,264,876,509,395,81,592,325\n" +
            "902,77,735,904,81,54,793,116,406,160,278,529,732,533,722,138,810,70,831,415\n" +
            "859,641,864,946,679,370,392,545,77,128,614,724,79,354,408,313,328,879,892,809\n" +
            "476,898,743,469,634,881,663,683,677,464,913,366,790,95,23,285,484,815,88,652\n" +
            "132,493,199,247,549,384,197,640,657,248,50,391,313,947,65,248,58,372,636,52\n" +
            "865,480,137,792,504,944,591,785,390,133,468,947,596,498,872,432,213,813,624,550\n" +
            "402,658,680,95,914,126,555,862,806,195,157,323,161,54,564,317,897,64,407,291\n" +
            "156,728,675,86,591,436,776,160,185,642,354,162,12,623,819,57,85,620,162,163\n" +
            "115,717,745,680,59,801,252,188,776,811,277,942,281,560,821,644,93,752,330,68\n" +
            "285,22,867,421,598,686,469,809,52,559,420,197,234,282,655,868,317,67,556,808\n" +
            "366,231,683,318,195,50,55,916,60,404,87,196,433,889,801,384,290,867,804,77\n" +
            "266,363,394,167,409,405,81,97,904,822,161,727,643,401,66,861,238,983,785,682\n" +
            "885,633,749,660,747,637,388,88,234,186,653,57,775,863,197,471,526,801,416,555\n" +
            "188,294,184,419,750,533,415,416,231,714,662,65,734,315,246,641,97,198,488,268\n" +
            "878,412,749,557,912,909,884,200,801,127,715,454,894,501,643,833,285,782,741,356\n" +
            "861,873,189,260,118,589,440,653,652,524,398,650,784,722,323,262,592,74,396,181\n" +
            "122,875,299,749,385,161,280,58,874,898,400,916,627,803,78,240,326,882,807,905\n" +
            "549,125,237,365,409,737,12,566,265,492,80,154,747,356,263,946,352,904,636,716\n" +
            "356,562,647,270,387,554,562,367,193,824,178,316,242,867,489,685,85,477,130,184\n" +
            "270,642,483,125,881,354,542,660,115,97,119,567,201,885,479,488,478,62,136,287\n" +
            "93,421,390,723,431,664,894,786,720,117,775,23,72,158,433,159,488,230,57,56\n" +
            "467,859,749,907,330,560,598,806,469,589,562,751,803,907,858,371,943,156,69,653\n" +
            "286,876,859,781,802,491,428,479,228,502,286,889,903,55,876,905,425,530,127,552\n" +
            "237,91,471,775,794,400,678,258,90,368,324,654,95,270,728,188,788,275,711,497\n" +
            "714,317,545,258,56,723,464,279,390,245,235,680,285,366,541,272,733,901,125,902\n" +
            "724,603,160,738,193,364,80,655,550,903,862,532,437,430,633,216,55,662,644,562\n" +
            "861,809,406,261,811,441,871,561,214,868,330,156,401,237,493,198,596,89,94,649\n" +
            "132,492,24,271,946,268,271,259,752,200,596,164,593,286,725,363,263,119,685,549\n" +
            "721,189,896,87,118,714,588,358,725,884,564,326,92,535,909,274,290,193,881,239\n" +
            "902,385,374,499,463,63,780,280,96,482,715,230,318,401,181,194,778,823,471,562\n" +
            "873,908,386,714,949,499,271,842,747,744,879,321,86,830,489,627,365,743,227,316\n" +
            "775,420,805,750,867,822,641,318,795,752,184,662,551,885,241,387,622,904,596,743\n" +
            "478,290,327,832,196,472,363,631,183,185,436,326,559,905,901,877,19,728,623,392\n" +
            "597,367,419,946,467,728,161,355,230,181,202,603,393,191,248,468,428,799,792,733\n" +
            "809,373,723,189,747,904,781,498,228,122,269,554,156,189,360,465,873,290,942,246\n" +
            "735,96,503,439,423,67,621,551,60,324,428,234,947,5,277,549,553,784,124,554\n" +
            "819,442,81,228,330,662,422,803,805,944,865,916,657,375,498,279,677,124,902,867\n" +
            "563,330,824,390,775,748,623,722,506,246,468,303,814,128,137,627,817,199,868,290\n" +
            "904,726,242,546,289,426,588,899,230,81,11,434,549,749,897,269,680,426,190,59\n" +
            "832,135,263,550,717,74,821,832,367,832,311,747,643,196,811,734,544,54,943,570\n" +
            "440,681,807,661,893,243,645,878,495,133,245,418,605,416,193,329,714,201,234,534\n" +
            "237,876,424,92,568,812,897,2,160,156,260,638,633,916,750,199,316,391,78,735\n" +
            "678,531,908,656,898,421,733,746,790,637,478,389,684,205,283,90,714,498,136,413\n" +
            "819,752,499,495,402,599,682,646,471,245,778,155,135,894,616,164,359,559,649,719\n" +
            "560,288,893,976,323,390,563,590,664,323,94,424,403,387,355,184,119,427,546,532\n" +
            "366,949,909,862,473,675,529,272,901,120,455,84,867,428,834,200,66,598,731,232\n" +
            "323,474,227,267,431,165,373,659,319,722,804,638,259,282,909,648,903,627,657,627\n" +
            "652,10,276,471,385,59,683,717,97,806,135,470,812,353,729,632,746,571,776,469\n" +
            "128,58,741,633,463,614,241,197,661,431,260,781,131,830,572,269,368,432,128,74\n" +
            "82,805,121,166,900,394,320,801,730,793,557,776,426,682,53,594,739,564,181,246\n" +
            "66,406,496,862,502,814,198,64,89,67,246,13,877,546,79,627,161,427,76,906\n" +
            "552,569,131,975,394,637,644,944,383,907,505,385,830,787,432,354,329,908,393,496\n" +
            "192,361,236,267,775,566,163,407,359,676,472,551,264,873,904,497,417,626,613,726\n" +
            "199,713,441,278,606,356,599,552,716,184,748,527,824,534,478,633,645,903,813,73\n" +
            "783,649,781,437,270,79,735,607,131,422,899,719,812,324,909,56,545,264,423,733\n" +
            "737,437,729,50,714,370,901,530,426,302,484,75,823,685,228,489,57,728,158,501\n" +
            "280,411,80,478,733,592,231,417,359,658,658,558,644,660,166,461,498,125,496,71\n" +
            "317,277,799,88,275,405,506,115,566,121,56,808,124,789,407,861,743,883,17,590\n" +
            "394,904,572,598,716,163,546,731,781,475,946,997,163,858,435,198,633,529,73,860\n" +
            "66,329,224,330,439,802,527,130,167,645,565,417,784,57,635,679,713,191,750,655\n" +
            "590,812,897,87,382,862,529,726,200,739,724,838,815,493,241,272,438,496,686,199").split("\\n\\n");

        var rRules = raw[0].split("\\n");
        var rYourTicket = raw[1].split("\\n");
        var rTickets = raw[2].split("\\n");

        Map<String, int[]> rules = new HashMap<>();

        for (var rule : rRules) {
            var t = rule.split(":");
            var name = t[0];
            var ranges = t[1].split(" or ");
            var rtoput = new int[4];
            var fromto = ranges[0].split("-");
            rtoput[0] = Integer.parseInt(fromto[0].trim());
            rtoput[1] = Integer.parseInt(fromto[1].trim());
            fromto = ranges[1].split("-");
            rtoput[2] = Integer.parseInt(fromto[0].trim());
            rtoput[3] = Integer.parseInt(fromto[1].trim());
            rules.put(name, rtoput);
        }

        List<Integer[]> validTickets = new ArrayList<>();

        for (var i = 1; i < rTickets.length; i++) {
            var ticketvalid = true;
            var numbersFromTicket = Arrays.stream(rTickets[i].split(",")).map(Integer::valueOf).toArray(Integer[]::new);
            for (var number : numbersFromTicket) {
                var valid = false;
                for (var rule : rules.entrySet()) {
                    if (number >= rule.getValue()[0] && number <= rule.getValue()[1] || number >= rule.getValue()[2] && number <= rule
                        .getValue()[3]) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    ticketvalid = false;
                    break;
                }
            }
            if (ticketvalid) {
                validTickets.add(numbersFromTicket);
            }
        }

        Map<String, List<Set<Integer>>> validIndexes = new HashMap<>();

        for (var rule : rules.entrySet()) {
            List<Set<Integer>> ruleMatches = new ArrayList<>();
            for (var numbersFromTicket : validTickets) {
                Set<Integer> validIndex = new HashSet<>();

                for (var j = 0; j < numbersFromTicket.length; j++) {
                    var number = numbersFromTicket[j];
                    if (number >= rule.getValue()[0] && number <= rule.getValue()[1] || number >= rule.getValue()[2] && number <= rule
                        .getValue()[3]) {
                        validIndex.add(j);
                    }
                }

                ruleMatches.add(validIndex);
            }

            validIndexes.put(rule.getKey(), ruleMatches);
        }

        Map<String, Set<Integer>> newValid = new HashMap<>();

        for (var ruledata : validIndexes.entrySet()) {
            newValid.put(ruledata.getKey(), ruledata.getValue().stream().skip(1)
                .collect(() -> new HashSet<>(ruledata.getValue().get(0)), Set::retainAll, Set::retainAll));
        }

        Set<Integer> mapped = new HashSet<>();

        Map<String, Integer> result = new HashMap<>();

        for (var i = 0; i < newValid.size(); i++) {
            for (var rule : newValid.entrySet()) {
                if (i + 1 == rule.getValue().size()) {
                    for (var oooo : rule.getValue()) {
                        if (!mapped.contains(oooo)) {
                            mapped.add(oooo);

                    if (rule.getKey().startsWith("departure"))
                            result.put(rule.getKey(), oooo);
                        }
                    }
                }
            }
        }

        for (var mmmm : result.entrySet()) {
            System.out.println(mmmm.getKey() + ": " + mmmm.getValue());
        }

        var tmp = rYourTicket[1].split(",");

        var re = 1L;

        for (var rrr : result.entrySet()) {
            re *= Long.parseLong(tmp[rrr.getValue()]);
        }


        System.out.println(re);
    }
}