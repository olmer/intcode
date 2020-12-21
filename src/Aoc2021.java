import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aoc2021 {
    public static void main(String[] args) {
        var raw = getRaw().split("\\n");

        Map<String, List<Set<String>>> groupedByAllergen = new HashMap<>();

        List<List<String>> allIngr = new ArrayList<>();

        for (var str : raw) {
            var a = str.split(" \\(");
            var ingr = a[0].split(" ");
            var alrgs = a[1].substring(9, a[1].length() - 1).split(", ");

            for (var alr : alrgs) {
                var e = groupedByAllergen.get(alr);
                if (e == null) {
                    e = new ArrayList<>();
                }
                var ns = new HashSet<>(Arrays.asList(ingr));
                e.add(ns);
                groupedByAllergen.put(alr, e);
            }

            allIngr.add(Arrays.asList(ingr));
        }

        Map<String, Set<String>> ingrWithAllergensGrouped = new HashMap<>();
        Set<String> ingrWithAllergents = new HashSet<>();

        groupedByAllergen.forEach((alrg, ingLists) -> {
            Set<String> i = ingLists.stream().skip(1)
                .collect(()->new HashSet<>(ingLists.get(0)), Set::retainAll, Set::retainAll);

            ingrWithAllergensGrouped.put(alrg, i);
            ingrWithAllergents.addAll(i);
        });

        var r = 0;

        for (var a : allIngr) {
            for (var b : a) {
                if (!ingrWithAllergents.contains(b)) r++;
            }
        }

        System.out.println(r);
        var curCount = 1;

        Map<String, String> canon = new HashMap<>();
        List<String> answ = new ArrayList<>();

        var skip = false;
        while (!skip) {
            skip = true;
            for (var ingrWithAll : ingrWithAllergensGrouped.entrySet()) {
                if (ingrWithAll.getValue().size() != curCount) continue;

                for (var l : ingrWithAll.getValue()) {
                    if (!canon.containsValue(l)) {
                        canon.put(ingrWithAll.getKey(), l);
                        answ.add(ingrWithAll.getKey());
                        skip = false;

                        for (var ingrWithAllt : ingrWithAllergensGrouped.entrySet()) {
                            ingrWithAllt.getValue().remove(l);
                        }

                        break;
                    }
                }
            }
        }

        Collections.sort(answ);

        List<String> answ2 = new ArrayList<>();
        for (var t : answ) {
            answ2.add(canon.get(t));
        }
        System.out.println(String.join(",", answ2));
    }

    private static String getRaw() {
        return "sjcvsr qxth vxvc xgthfcq bstzgn thxgbc gzmp qbdcbd hn hjbvv srxlj cnssqf rsjsg skk kxbzjjv hsszgc kzjdb dnrp gssbdjs fjpl njlf mnhcj lhcxr vkdxfj tsxcbqd kxkjl cbhbc rzvkp lgkf chph tbcb lzjhs kzrjg lfclq gdct dgsdtj rklpqh hpvkt sfxgdqk zqpngk klbh jnltzb jldpm lzqvj nqntx fsnk cdbt nlmmqm xbbds hvpg vkcggp knffs bbxgv kgrpxz gvvjkr zkxbcrf ljsv bfbj kmmqmv tbpmpt bsfqgb (contains fish)\n" +
            "njmpxq kxkjl nknph zcxqs cftnx mnhcj vkdxfj gssbdjs qbdcbd hn sbsc jbkkc qmtjm skk czvjd kzrjg rxshkqck bsfqgb ljsv gzmp sfxgdqk pfst lmk srznr srxlj bhdtv xdqr zqpngk fvdnq zlttx dcskm nlmmqm kmkr sjcvsr mdgn qbnplg bvclp lbjdq jldpm jnqdg qftnls lvzrzs cbhbc vxvc vkcggp dgsdtj hjbvps kpksf kcsxk kkcnc tsxcbqd bstzgn gj rzvkp skckcr qxth lfclq qdrm cdzk (contains peanuts, soy)\n" +
            "rklpqh cbhbc zdfz qgcbdbk njc vkdxfj csbrnpd krt tppbr qtckxl pfst sczl jbkkc gvvjkr sjcvsr fgckh dpdj gbvfp gj lfmp lvsclc sdpg mvhq lbjdq cnssqf fgtkrt kzjdb cdbt hjbvps qxth bstzgn qbdcbd qftnls mqvhl zvq vkcggp czfsf jldpm qbxt pfts lsdzt szdc sfxgdqk cxs zcxqs xbbds rzvkp npnn bjv bjcmq bsfqgb nknph cmvj lzjhs chl kpksf kmmqmv xdqr gdkdn fgkhl flkbrt gqqvt chph vxvc qmtjm tnxzs qv kczhdtn hpvkt kxbzjjv hsszgc cjgdl hn qbnplg gmj hmq pqvfl pfhm xpnhkjt (contains soy)\n" +
            "sjcvsr znkf dcskm hmq sczl bjcmq szdc hvppggp bzn dzdhh htdzz bstzgn rklpqh kpksf dgsdtj gj jldpm chph zlttx gdct zkxbcrf bhlz flkbrt kzrjg tpjf lzjhs vtrhhd hn bfbj tppbr cnqlt sdjk qftnls cjgdl qbnplg bbxgv kmmqmv tpnf mqvhl cftnx nlqtn lvg ljsv njc srqgvhd hbjdh pfd gbvfp bvclp xdcz fjpl vkdxfj rzvkp pfhm thxgbc hjbvps xzlfmg vkcggp dpdj sfxgdqk lfmp sdpg gzmp fpqmhm fhb srznr gbpvgsx lgkf zvq sjvp hsszgc gvvjkr czvjd vlclv hpvkt lmlhfzv sbsc mnhcj xpnhkjt pkrpgrbs bjtkqj msjdqn lhcxr (contains peanuts, eggs, nuts)\n" +
            "bsfqgb nlmmqm fgkhl cjgdl kczhdtn kmmqmv zkvt hjbvps dgsdtj hjbvv tnxzs pfst sjcvsr jbkkc qgcbdbk chl tnzmd dcskm nqntx gzmp fpqmhm hvpg qzfqst pfhm kzht lzjhs jnqdg rklpqh gmfxxc hn xzlfmg bstzgn zzdm lfclq lzqvj nlqtn fgckh zlzkfc tpnf srxlj sdpg dnrp cdbt lfmp njlf bhdtv szdc tsxcbqd vkdxfj (contains sesame, shellfish, soy)\n" +
            "lgkf zlzkfc fdhzxt chl lvsclc tppbr qpl cdbt hjbvv vkdxfj qv lzjhs gdct fgkhl fjpl kmmqmv hn thxgbc pqkmm gbpvgsx jnqdg bjtkqj kcsxk qbdcbd srxlj czvjd dvrvqd lbjdq qbxt skckcr bsfqgb hmq xjd xdcz qtckxl bhlz gj tkfpb tnzmd lzmqmr lvzrzs lhcxr kmkr bqrjt xbbds rzvkp pfd hlhblrj fsgjkd srqgvhd lfclq bdsjl pkrpgrbs fpqmhm cnssqf mrfd nknph rsjsg jbkkc rklpqh szdc srznr kpksf hbjdh sjcvsr dgsdtj njc vjvhfrvt (contains fish)\n" +
            "qmtjm kpksf kgrpxz vkdxfj gssbdjs hsszgc qzfqst hbjdh jldpm lvzrzs gqqvt cjgdl gmj xkjlclk lvg bhlz xpnhkjt fvdnq tpnf qv dnrp fhb gdct fdhzxt gzmp bsfqgb gmc cnqlt qdrm pp rzvkp bjcmq skckcr dgsdtj bzn hn lmk chl qbdcbd czfsf jnqdg hlhblrj pfst bstzgn kzrjg cftnx njc njlf zkxbcrf czvjd xbbds tssd htdzz qtckxl rszhn skk qbxt kmmqmv (contains soy)\n" +
            "vjvhfrvt cbhbc kxkjl dgsdtj hpvkt kkcnc tkfpb tssd klbh bsfqgb sbsc zxmvt cdbt kxbzjjv sjcvsr flkbrt lmk tccx fgtkrt qmtjm czfsf zlttx jnqdg fpqmhm hn srxlj xkjlclk qbdcbd jnltzb lgkf chph kcsxk kmmqmv qbxt htdzz mnhcj pkrpgrbs gqqvt bjtkqj gmc kgrpxz srznr gssbdjs fvdnq cbxrs zkvt kpksf bstzgn jldpm bbxgv rxshkqck qxth bqrjt csbrnpd fgkhl qrcr mvhq cftnx mdgn qftnls (contains sesame, nuts)\n" +
            "rzvkp vkcggp zvq zgkj hpvkt hmq dgsdtj qrcr cftnx hn hjbvps lvzrzs vkdxfj srxlj kmmqmv chl lzqvj pfst bstzgn szdc vxvc lmlhfzv dnrp ttdfq hlhblrj bvclp kzjdb gssbdjs mvhq sjcvsr njmpxq gvvjkr pkbkgb qgcbdbk dvrvqd bdsjl gmc nlqtn gbvfp fpqmhm dzdhh mxzk bhdtv gmfxxc fffbl tppbr znkf bqrjt pfd fgckh xdqr thxgbc fsnk kpksf cbxrs mqvhl nlmmqm rszhn xjd bhlz (contains soy, peanuts)\n" +
            "pfhm cnssqf qbxt kpksf vkdxfj bjtkqj gmj lvzrzs qdrm tnxzs qxth tbpmpt njc krt xdqr cxs xgthfcq bsfqgb gmfxxc sjvp dnrp tssd fjpl cmvj kkcnc nknph bvclp dgsdtj tpnf bjv qmtjm hn gbpvgsx sjcvsr qbdcbd kzht gdct ttdfq hbtm srznr vkzxr bbxgv tpjf zqpngk xdcz gzmp skckcr dpdj jnltzb zvq mnhcj kmmqmv lvg cftnx (contains fish, wheat)\n" +
            "bstzgn qftnls kxkjl vkdxfj srznr rxshkqck tppbr hmq fhb qbxt htdzz nqntx kxbzjjv msjdqn vxvc kzht sjvp pp csbrnpd sjcvsr zvq fffbl hn mdgn hbjdh rklpqh kpksf pfd gdct hpvkt gqqvt lfmp szdc bjv hvppggp dgsdtj kmmqmv bqrjt mqvhl cdzk bjtkqj fgckh lhcxr sfxgdqk fpqmhm jldpm dnrp gzmp npnn znkf lmk pqvfl nlqtn czfsf ttdfq dcskm hjbvps tkfpb cbxrs njc bhdtv lsdzt kcsxk gdkdn (contains sesame, eggs, fish)\n" +
            "kpksf fjpl chl rklpqh bdsjl xbbds jnqdg kmkr srqgvhd cbhbc qmtjm hn cjgdl qxth zdfz mnhcj bzn thxgbc vkzxr bhdtv dnrp pkbkgb hvppggp czfsf sjvp qv qgcbdbk zcxqs jnltzb nqntx fgtkrt kczhdtn cbxrs bhlz ttdfq vxvc czvjd jbkkc tbcb cdbt pfst bsfqgb gmqgbcz srznr zlzkfc vkdxfj srxlj xzlfmg kkcnc sjcvsr klbh tpnf cmvj mrfd lfclq lsdzt zgkj fsnk gzmp tnzmd fffbl rzvkp sdjk vtrhhd zkxbcrf kmmqmv lmk dgsdtj skk xgthfcq flkbrt (contains shellfish, fish, peanuts)\n" +
            "njmpxq dpdj kmmqmv hbtm sjvp cdbt kzjdb zcxqs cjgdl gmc tbcb rsjsg czvjd cftnx tpjf xbbds kkcnc gzmp fpqmhm sdjk kzht qzfqst nlqtn pqvfl qbnplg vkcggp zzdm nknph bdsjl sfxgdqk pkrpgrbs cdzk kmkr skk sjcvsr lvsclc gmqgbcz qftnls jldpm cnqlt lzqvj pfhm flkbrt skckcr hn vxvc bsfqgb bhdtv kpksf pqkmm ttdfq sbsc kczhdtn csbrnpd sdpg lvzrzs lmk bzn qtckxl njlf srznr cnssqf xzlfmg dvrvqd bqrjt qxth gvvjkr srqgvhd mrfd pkbkgb rxshkqck sczl dgsdtj lhcxr hjbvv bstzgn hbjdh (contains eggs, sesame)\n" +
            "pp lsdzt pkbkgb gdkdn jnqdg hvppggp kpksf thxgbc gj chl kgrpxz mnhcj dgsdtj srxlj zdfz sjcvsr rsjsg xkjlclk nlmmqm bfbj zlzkfc bdsjl fgtkrt hbjdh zkxbcrf zqpngk bstzgn lzqvj vkzxr skk fffbl bqrjt pqkmm cxs gmfxxc gbpvgsx lmlhfzv kmmqmv dnrp lhcxr dpdj jnltzb tpjf bhdtv fdhzxt msjdqn bjv bhlz bvclp ljsv lfclq cnqlt bjtkqj kzht klbh znkf qdrm cnssqf fgkhl hmq srznr kcsxk zgkj qbnplg hn zcxqs sbsc cftnx gssbdjs flkbrt lzjhs bsfqgb gmj hjbvv njlf lbjdq kmkr qzfqst dcskm kxkjl tppbr bbxgv mdgn qbxt qv pqvfl qrcr (contains wheat, nuts)\n" +
            "xzlfmg sfxgdqk hvppggp njmpxq fdhzxt bjv cbxrs czvjd zlzkfc vkdxfj hmq jnqdg dpdj kzrjg bstzgn rklpqh qmtjm hn pfts tsxcbqd kmmqmv mrfd dzdhh bfbj rxshkqck tpnf bjcmq fgtkrt dgsdtj sdjk cxs zqpngk tssd qgcbdbk kpksf sjcvsr mxzk (contains eggs, fish, peanuts)\n" +
            "bhdtv qftnls hbjdh lfclq kpksf bsfqgb nlqtn lzjhs lgkf pqvfl zqpngk vjvhfrvt sjcvsr vkdxfj mqvhl fpqmhm fgtkrt bdsjl gmqgbcz bqrjt bstzgn kmmqmv sjvp kkcnc pkrpgrbs njlf bzn fgckh fgkhl tnzmd zkxbcrf kczhdtn xdqr kgrpxz nqntx rklpqh kzjdb gssbdjs zxmvt sbsc tkfpb htdzz xjd nlmmqm gmfxxc tccx kzht xdcz dgsdtj gj pfd jldpm vkcggp hsszgc fffbl dzdhh gvvjkr (contains shellfish)\n" +
            "gmc xkjlclk bsfqgb rklpqh qbdcbd lvg lgkf bstzgn hbjdh srznr hjbvv htdzz vkzxr bfbj gbpvgsx nlqtn dgsdtj flkbrt lhcxr czvjd kxbzjjv vtrhhd csbrnpd xjd zcxqs nqntx qgcbdbk kgrpxz bbxgv pkrpgrbs njc pfhm srxlj kzjdb mdgn fvdnq sjcvsr vkdxfj cjgdl hpvkt fjpl srqgvhd kpksf qdrm hn mxzk zkvt ljsv sdpg tppbr msjdqn pqvfl pfd (contains wheat, eggs)\n" +
            "lzjhs rxshkqck kzjdb dgsdtj ljsv nlmmqm vjvhfrvt jldpm zcxqs cbxrs xzlfmg hmq tbcb mvhq gdkdn qftnls fgckh kpksf fhb pkrpgrbs zvq rzvkp znkf hsszgc cdzk lfmp tnzmd sdpg zkvt qbnplg bstzgn qzfqst mrfd knffs fgtkrt kmkr hvppggp bjcmq kzrjg hbtm bjtkqj zgkj lhcxr bsfqgb sdjk pqkmm gdct pfd gj npnn kmmqmv cbhbc tsxcbqd tssd gqqvt lvzrzs sjcvsr zlttx fdhzxt xjd vkdxfj vxvc lvsclc flkbrt sfxgdqk (contains sesame, shellfish, fish)\n" +
            "kzjdb tnzmd rsjsg zkvt gqqvt cbxrs klbh thxgbc lgkf zlttx vxvc tssd hpvkt nlqtn fsnk szdc sjcvsr hbtm gzmp hn flkbrt gmqgbcz sdpg njlf kzrjg mnhcj nknph dgsdtj bstzgn bdsjl vkdxfj xbbds lvzrzs bsfqgb gmfxxc ljsv lvsclc qgcbdbk mqvhl rxshkqck nqntx fsgjkd ttdfq bjcmq vkzxr jldpm lfmp kpksf tkfpb njmpxq (contains fish, nuts)\n" +
            "xbbds hvppggp vxvc cxs bjv hjbvv bfbj dvrvqd hbjdh jbkkc zgkj lfclq xdcz zqpngk xdqr mqvhl gdkdn gmfxxc gj fhb zcxqs bstzgn kpksf jldpm fgtkrt gbpvgsx thxgbc pqvfl klbh bdsjl sjcvsr rxshkqck sfxgdqk gbvfp srqgvhd bhlz zxmvt znkf czvjd tsxcbqd pqkmm srznr lsdzt kmmqmv kczhdtn hn lzqvj sbsc srxlj lzjhs bsfqgb mvhq fgckh rzvkp fjpl jnltzb rsjsg msjdqn hlhblrj fvdnq dgsdtj chl bjtkqj zkxbcrf gssbdjs vjvhfrvt qtckxl (contains wheat, nuts)\n" +
            "lgkf qbdcbd gdct tssd kczhdtn kgrpxz gmfxxc hlhblrj kpksf kmmqmv fsgjkd qbxt kzjdb vkcggp fhb jldpm bfbj srqgvhd mdgn vtrhhd dgsdtj jnltzb lvzrzs fgtkrt gj cxs hn knffs tbpmpt bstzgn tccx ljsv skk kzrjg dzdhh cftnx znkf csbrnpd cjgdl bsfqgb fsnk dpdj bjtkqj tbcb fgckh xdcz lzjhs lmlhfzv qzfqst rxshkqck zxmvt lfmp bvclp fffbl bzn vkdxfj njlf zlttx pqvfl vlclv qmtjm chl mrfd qv lzmqmr tsxcbqd nknph klbh pkbkgb sczl qrcr gmj srxlj gvvjkr tppbr xbbds srznr bhlz (contains eggs, wheat)\n" +
            "tssd xzlfmg zdfz chl kzrjg kpksf rxshkqck hlhblrj gssbdjs pp nknph tccx bjcmq xkjlclk xgthfcq hn bhlz lzjhs xdcz vkcggp skk tpnf bfbj ttdfq lvsclc bhdtv sjvp lgkf gdkdn gqqvt sjcvsr zkvt zcxqs fjpl kmmqmv qdrm vkdxfj hbjdh bstzgn bjv bbxgv pfts pfd kzht xpnhkjt cmvj srxlj nqntx skckcr dcskm mvhq dgsdtj fffbl (contains soy)\n" +
            "gzmp kzht sjvp hbjdh qzfqst jbkkc sdjk mxzk kmkr hn fpqmhm vkdxfj tccx njmpxq zkxbcrf sjcvsr bvclp mdgn srqgvhd bzn lfclq qgcbdbk czvjd gmc flkbrt hjbvv mrfd fsnk zgkj gdkdn dvrvqd lbjdq lvzrzs tnxzs cmvj dgsdtj gmqgbcz vjvhfrvt rklpqh xbbds tpnf kpksf kmmqmv srxlj vkzxr hmq zvq tbpmpt htdzz qbnplg sdpg hvppggp cnssqf qmtjm lzjhs bstzgn hlhblrj kgrpxz gmfxxc pkbkgb qpl mvhq zkvt gbpvgsx zdfz bjcmq xdqr thxgbc kxkjl bdsjl znkf xjd bfbj rzvkp tbcb npnn kkcnc rsjsg (contains peanuts)\n" +
            "zzdm qzfqst kmmqmv bhdtv xgthfcq hbjdh npnn hn gmfxxc ljsv tpnf szdc sdjk bjtkqj nlmmqm sczl bsfqgb skckcr zlzkfc gbvfp fgckh czfsf kzht kpksf pp csbrnpd gqqvt gssbdjs gmc krt xdcz sdpg tccx bhlz bjv lbjdq ttdfq hpvkt tbpmpt rsjsg zxmvt thxgbc gbpvgsx dnrp fffbl srznr bbxgv xkjlclk skk cftnx fsnk jnqdg qbnplg bdsjl bqrjt fhb qbxt zvq sjcvsr qpl zgkj xzlfmg vkdxfj cnssqf msjdqn fvdnq hvppggp sbsc tssd dpdj fdhzxt znkf mqvhl xdqr tnzmd lgkf cmvj mnhcj lvzrzs dgsdtj qftnls srxlj gdct dcskm hsszgc fgkhl (contains soy)\n" +
            "kmmqmv tbcb sczl csbrnpd cxs qxth gmc zlzkfc vxvc srxlj qrcr vkzxr hn vtrhhd gmfxxc hmq sjvp sjcvsr dvrvqd tssd bdsjl qzfqst gssbdjs thxgbc chph bqrjt tkfpb xzlfmg knffs fsgjkd vkdxfj sdjk rszhn zkvt lmlhfzv bjcmq zdfz gzmp xdcz qbdcbd mvhq fhb dzdhh lvg hsszgc nlqtn zzdm dcskm tnxzs hjbvps hpvkt cbhbc bstzgn gdct kxkjl fvdnq skckcr chl nknph kpksf zqpngk sbsc kmkr dgsdtj hbtm pqkmm qtckxl tbpmpt srqgvhd qv bhlz pqvfl pfd fjpl pfhm (contains peanuts)\n" +
            "hn sjcvsr bstzgn gvvjkr dgsdtj mnhcj sdjk srqgvhd kmmqmv qv vkdxfj tnxzs gdct pqvfl rzvkp rklpqh bvclp mqvhl fjpl vxvc tnzmd gqqvt bbxgv kxbzjjv qrcr tccx vtrhhd gj rszhn dpdj lbjdq cbhbc cnssqf pfts hmq zzdm qmtjm czvjd bdsjl bqrjt vjvhfrvt hbtm znkf fhb mxzk xpnhkjt mrfd chph kpksf msjdqn njc njmpxq pkrpgrbs htdzz vkcggp cdbt jnqdg nlmmqm pqkmm bfbj fgkhl xdqr qpl lvsclc tbcb hbjdh bhlz zlzkfc qbnplg hjbvv lfmp (contains eggs, soy, nuts)\n" +
            "gmc qdrm tpnf bhdtv hn fgtkrt fhb kpksf nqntx bdsjl cnqlt bsfqgb zzdm rxshkqck fpqmhm pfd sjcvsr hvpg gmj dgsdtj dpdj klbh thxgbc bstzgn hvppggp tbcb cjgdl sbsc skckcr sczl tpjf xpnhkjt lsdzt vkcggp chph czfsf sjvp qpl pkbkgb nlmmqm ttdfq zqpngk czvjd hbtm rszhn bhlz lmlhfzv zlttx xkjlclk dcskm bbxgv zkxbcrf zlzkfc knffs kmmqmv lvzrzs kmkr bzn rzvkp lzqvj tsxcbqd fgkhl srqgvhd sfxgdqk szdc (contains eggs, fish)\n" +
            "chl xdqr hn fffbl bvclp kmmqmv qzfqst gj cbxrs tpnf sjcvsr sbsc pfd njc skckcr xbbds czvjd hbjdh vkdxfj njlf dgsdtj tsxcbqd hsszgc cmvj hbtm fsgjkd hvpg zlttx dzdhh fgkhl gbvfp sczl tpjf fgckh lgkf hmq kzjdb skk kzrjg hjbvv gqqvt htdzz zkvt xdcz qdrm srqgvhd pfhm vkcggp dpdj lmk tnxzs qgcbdbk xjd bhlz njmpxq zlzkfc vjvhfrvt thxgbc kpksf jnltzb znkf kcsxk qftnls vkzxr fgtkrt bstzgn qv hvppggp cbhbc lbjdq cftnx gmc mrfd zgkj pfst vlclv nlqtn cdzk cnssqf pp lvg bqrjt (contains eggs, soy, fish)\n" +
            "kzht bzn sbsc tbcb fsnk xdcz lfclq cftnx sdpg fvdnq tnzmd nqntx kmmqmv ttdfq cbhbc jbkkc sjcvsr qzfqst zzdm lfmp mnhcj bhdtv mxzk qftnls dpdj vkdxfj hn nlmmqm pfd rzvkp lsdzt kkcnc lgkf dgsdtj kgrpxz chl jnqdg kzrjg fgckh skckcr lvg vlclv gmj msjdqn cdbt lzqvj mdgn bjcmq tssd zlttx pkbkgb bstzgn xdqr lhcxr sjvp lvzrzs gssbdjs jldpm qpl xzlfmg bsfqgb gdct dcskm zxmvt cnssqf qtckxl xjd vjvhfrvt bqrjt fsgjkd njc flkbrt srqgvhd tppbr kxbzjjv vtrhhd fffbl qrcr cnqlt (contains peanuts, soy, wheat)\n" +
            "vkdxfj pqvfl bqrjt xbbds lfmp jldpm szdc njmpxq njc xjd mrfd hlhblrj mvhq cdbt cnqlt jnltzb hvppggp rszhn bhlz nlmmqm pfhm dvrvqd hn cftnx xdcz zlttx vjvhfrvt hjbvv ttdfq cmvj fgckh qbdcbd sdpg bjv lvg bdsjl tbcb xzlfmg lmlhfzv bstzgn bhdtv kxbzjjv gmqgbcz njlf kmmqmv sjcvsr hjbvps rsjsg dgsdtj gssbdjs chph kpksf pfd bfbj chl (contains fish, sesame, nuts)\n" +
            "skk vxvc kmmqmv hvpg rxshkqck bhlz gdct njc kxbzjjv zcxqs gmfxxc lbjdq pp lmlhfzv hlhblrj chl bjv msjdqn bvclp fsnk chph nlmmqm zvq fpqmhm knffs lgkf hjbvv kpksf hn xjd gmc mxzk tssd sjcvsr czvjd sczl fvdnq bsfqgb lvsclc vkdxfj kzjdb bstzgn lfclq bjtkqj fffbl kxkjl hjbvps rsjsg hvppggp mrfd cdbt lvg vlclv lsdzt dzdhh (contains shellfish, eggs)\n" +
            "zvq qbxt vkzxr bhlz zqpngk lfclq kzht gdkdn dzdhh xjd kcsxk qrcr vkdxfj gmfxxc cbhbc vxvc bsfqgb rszhn mdgn jnqdg fsgjkd dnrp znkf cdzk ttdfq gmqgbcz bstzgn kkcnc bjv hn chl kzrjg qbnplg njlf sjcvsr mvhq sdjk dgsdtj chph qftnls lgkf hsszgc rsjsg qxth kmmqmv (contains fish, soy)\n" +
            "lmlhfzv kczhdtn rklpqh lhcxr bvclp cnqlt tnxzs dnrp sczl kpksf kkcnc ttdfq jldpm gbvfp cdzk cdbt sfxgdqk njlf szdc bstzgn kzht sjcvsr bqrjt kmmqmv vkdxfj cbhbc zqpngk pqvfl npnn skckcr hsszgc cftnx pfts qdrm jnqdg pfst zkxbcrf cnssqf zdfz zcxqs qmtjm mvhq pp srqgvhd flkbrt lbjdq bjtkqj xdcz rsjsg dgsdtj gdct kgrpxz hvpg zzdm vjvhfrvt vtrhhd csbrnpd bsfqgb gssbdjs pkbkgb kcsxk dpdj hpvkt fsgjkd bhlz (contains sesame, peanuts)\n" +
            "tbcb mxzk knffs vkdxfj zlzkfc tccx chph nknph kmmqmv bjcmq qbnplg kxbzjjv hpvkt fvdnq pp sdpg pfts chl sjcvsr gvvjkr dgsdtj sfxgdqk dzdhh fffbl gj klbh cftnx mqvhl tppbr hjbvv gmj qv nlqtn bstzgn zcxqs xdcz njmpxq nqntx lgkf rszhn fdhzxt skckcr srqgvhd zlttx hmq pfst tpjf qbdcbd npnn bsfqgb hn csbrnpd cxs krt njlf hjbvps vxvc qzfqst mvhq flkbrt xpnhkjt kcsxk htdzz bbxgv fgtkrt dcskm tsxcbqd lsdzt kkcnc dvrvqd (contains sesame, soy, peanuts)\n" +
            "bzn lgkf qmtjm zkvt cjgdl pp bjcmq fhb hjbvv lvsclc hlhblrj cdzk sczl jnqdg chl lmlhfzv kmmqmv bsfqgb hvppggp lzjhs zzdm pqkmm bstzgn qv hvpg tssd fjpl hn lzqvj tpnf rklpqh sbsc xkjlclk lzmqmr jbkkc mvhq kpksf kxbzjjv dpdj njlf xdqr xzlfmg klbh xpnhkjt srqgvhd kcsxk csbrnpd zvq vjvhfrvt gmfxxc gj fsgjkd qgcbdbk gdkdn zgkj qftnls kzht zxmvt bvclp gdct dgsdtj gqqvt zqpngk cnssqf qpl rsjsg nlmmqm bqrjt fpqmhm vkdxfj tsxcbqd (contains eggs)\n" +
            "pfhm qpl lzmqmr bhdtv gqqvt rzvkp pqkmm qrcr vjvhfrvt qftnls zkvt vkdxfj zcxqs srznr hpvkt tbpmpt klbh thxgbc krt fjpl lzqvj dgsdtj pfd hjbvps chph cdzk rxshkqck kpksf pkbkgb kcsxk zdfz bsfqgb gbvfp mdgn zgkj qtckxl tsxcbqd xjd hsszgc rszhn flkbrt kmmqmv fsnk lvg kzht cdbt sjcvsr gdct fhb bstzgn qxth nlmmqm lmlhfzv dnrp njc (contains fish, nuts, shellfish)\n" +
            "kzjdb kpksf qtckxl zzdm bsfqgb lgkf bfbj mxzk lzmqmr hn nqntx gbvfp pfts cmvj znkf srxlj kmmqmv cdzk czfsf xbbds tpnf fgtkrt gvvjkr rsjsg hjbvps jnltzb kczhdtn fgkhl qxth cbxrs xkjlclk sjcvsr bqrjt gmqgbcz vkdxfj fjpl xjd tbpmpt zkxbcrf bstzgn qpl zvq lzjhs knffs bjv xdqr rklpqh lhcxr npnn pqvfl qbdcbd gbpvgsx pqkmm srqgvhd pfd (contains wheat, peanuts, shellfish)\n" +
            "npnn fffbl gj hvppggp sdjk tppbr njmpxq cxs lgkf zvq znkf cnqlt skckcr sjcvsr vkdxfj bzn tnzmd fsgjkd tnxzs bbxgv kmmqmv kkcnc mvhq fgckh rzvkp vxvc zcxqs bvclp krt lzqvj lhcxr srxlj bsfqgb gssbdjs bjtkqj nknph dgsdtj njc qbnplg bstzgn ljsv lmk tbpmpt lmlhfzv szdc hjbvps cbxrs pfd gvvjkr cjgdl srznr fgtkrt kgrpxz qzfqst zzdm czfsf cdbt kcsxk kpksf bqrjt pkbkgb lfclq qgcbdbk kzht (contains shellfish)\n" +
            "fdhzxt lhcxr zcxqs kxbzjjv skckcr kcsxk kkcnc czvjd qrcr kxkjl npnn kpksf vlclv pqvfl dzdhh qpl thxgbc bjv tbcb hbjdh lvg bsfqgb sdjk lvzrzs tsxcbqd bstzgn ljsv mxzk lgkf dgsdtj zkvt sjcvsr tkfpb sfxgdqk xdqr jnltzb bjtkqj lfmp bjcmq gssbdjs hmq lmlhfzv zgkj tnzmd rklpqh chph hn fsgjkd cxs csbrnpd fpqmhm kmmqmv (contains sesame)\n" +
            "bhdtv kmmqmv jldpm lmk xpnhkjt fgtkrt lgkf flkbrt cbhbc kzht fvdnq srqgvhd skk kczhdtn qv lfclq gmqgbcz rszhn sjcvsr fgckh vkdxfj hbtm bstzgn hbjdh lzmqmr fjpl qbxt qgcbdbk kpksf pfts bsfqgb bhlz hvpg dpdj cmvj zlttx nlmmqm bzn jnltzb njc gbvfp dgsdtj xdqr pfst rxshkqck kzjdb vlclv gzmp hlhblrj sczl xdcz (contains sesame, nuts)\n" +
            "lfclq bfbj zlttx tbpmpt nqntx gssbdjs gmc rklpqh rzvkp hlhblrj zkxbcrf tsxcbqd bhlz srqgvhd sjcvsr tbcb flkbrt bzn kcsxk kxkjl zlzkfc tkfpb srznr fsnk kzht fsgjkd fgtkrt kpksf dgsdtj tnxzs xkjlclk qxth fpqmhm bqrjt vkdxfj fdhzxt qbdcbd htdzz gbpvgsx sfxgdqk jldpm nlmmqm sdjk bstzgn pkrpgrbs gj kczhdtn cdbt xjd lvzrzs qtckxl kmmqmv mqvhl cjgdl kxbzjjv lgkf lvg vjvhfrvt lbjdq fgckh fgkhl dnrp qdrm zkvt hn ljsv bjv tccx knffs njlf pfhm vtrhhd rsjsg tnzmd (contains fish, peanuts, eggs)\n" +
            "srqgvhd rszhn hn tssd cbxrs dgsdtj zlttx szdc skckcr vjvhfrvt lfmp cbhbc chl czvjd gmqgbcz bstzgn pqvfl bfbj tbpmpt fffbl lfclq dzdhh znkf lbjdq mdgn nknph msjdqn qv jbkkc xgthfcq pkbkgb kczhdtn vtrhhd dcskm lvg pfhm gmc dvrvqd mqvhl kgrpxz xdqr tnxzs sjcvsr tnzmd kzrjg tpjf thxgbc ttdfq jnltzb qrcr tkfpb fvdnq vkdxfj kmmqmv kpksf nlmmqm gqqvt hpvkt (contains nuts, peanuts)\n" +
            "tppbr dgsdtj cdbt gmj gzmp xgthfcq ljsv kkcnc qbnplg lfmp kmmqmv kpksf kzrjg gbpvgsx qrcr lfclq gmfxxc vjvhfrvt lvzrzs gdkdn vkcggp sjcvsr zkvt bdsjl kmkr dvrvqd bsfqgb sdjk fvdnq bbxgv kczhdtn krt srznr zzdm cbxrs hsszgc qzfqst nknph qbxt zlttx zqpngk bstzgn tccx hn tbcb mqvhl bqrjt hmq pkbkgb cjgdl fffbl pfts cbhbc (contains peanuts, shellfish)\n" +
            "fjpl lbjdq bzn hn fvdnq hlhblrj gdkdn lzmqmr mdgn hbjdh lzqvj sbsc gmj xdqr xjd bbxgv tkfpb lmk sjcvsr dzdhh njlf cdbt xdcz qftnls pkrpgrbs cdzk lvzrzs msjdqn njmpxq bfbj hjbvps npnn zkxbcrf mvhq tbpmpt bstzgn bvclp zzdm mxzk gbvfp tpjf dnrp tbcb cmvj lvsclc fpqmhm xgthfcq vtrhhd fsgjkd pfd pfts tppbr fgkhl zlzkfc kmmqmv qpl ljsv sjvp qdrm nlmmqm rklpqh kpksf fsnk dgsdtj lzjhs vkdxfj kxbzjjv sdjk cbxrs (contains shellfish, peanuts)\n" +
            "mdgn qxth bhdtv njmpxq kczhdtn zgkj xdqr bzn dpdj zcxqs xpnhkjt nknph tbcb sjcvsr dgsdtj rszhn knffs lsdzt qdrm bqrjt bdsjl kmkr bstzgn vkdxfj pfhm chl bbxgv kpksf nqntx fsnk qbnplg tpnf bjtkqj rxshkqck lfclq lmlhfzv fvdnq hbtm kzht hn fgckh tssd gzmp lvg kzjdb sjvp gdct mqvhl hbjdh pkbkgb kmmqmv pp gj dzdhh jnltzb cxs srxlj kzrjg gmqgbcz (contains shellfish, sesame)\n" +
            "lzjhs kmkr kcsxk lzmqmr bfbj kxkjl hlhblrj pfst tppbr zxmvt bbxgv pfd gdct bqrjt pfhm zlzkfc kmmqmv ttdfq dgsdtj fsgjkd bjtkqj lvg gqqvt tccx sjcvsr kzrjg kxbzjjv gj bjv tbpmpt hjbvv kpksf vkcggp bstzgn cdbt vkdxfj fsnk tnxzs nlmmqm vxvc hpvkt tsxcbqd lmk pkrpgrbs gmc rxshkqck zvq kzjdb vlclv tnzmd sczl kczhdtn pp cjgdl kgrpxz fvdnq fgckh pkbkgb csbrnpd jbkkc cnqlt bdsjl fjpl sfxgdqk xdcz hmq fffbl lgkf hn cdzk lhcxr kkcnc (contains eggs, fish, shellfish)";
    }
}
