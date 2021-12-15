import java.util.ArrayList;
import java.util.PriorityQueue;

class Node2115 implements Comparable<Node2115> {
    private final int x;
    private final int y;
    private final int value;

    public Node2115(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Node2115 node2115) {
        return value > node2115.getValue() ? 1 : -1;
    }
}

public class Aoc2115 {
    public static void main(String[] args) {

        var input = getInput();
        var ssize = input.length;
        var size = ssize * 5;

        var targety = size - 1;
        var tagetx = size - 1;

        var data = new int[size][size];
        var visited = new boolean[size][size];

        for (var i = 0; i < ssize; i++) {
            for (var j = 0; j < input[i].length(); j++) {
                var dd = input[i].charAt(j) - '0';

                for (var ii = 0; ii < 5; ii++) {
                    for (var jj = 0; jj < 5; jj++) {
                        var val = dd + ii + jj > 9 ? dd + ii + jj - 9 : dd + ii + jj;
                        data[i + ssize * ii][j + ssize * jj] = val;
                    }
                }
            }
        }

        var openNodes = new PriorityQueue<Node2115>();

        openNodes.add(new Node2115(0, 0, 0));
        visited[0][0] = true;

        var r = 0;
        var coords = new ArrayList<Integer>() {{
            add(1);
            add(0);
            add(-1);
            add(0);
            add(0);
            add(1);
            add(0);
            add(-1);
        }};

        while (openNodes.size() > 0) {
            var node = openNodes.poll();
            r = node.getValue();
            if (node.getX() == tagetx && node.getY() == targety) {
                break;
            }

            for (var i = 0; i < coords.size() - 1; i += 2) {
                var ny = node.getY() + coords.get(i);
                var nx = node.getX() + coords.get(i + 1);
                if (ny < 0 || nx < 0 || ny >= data.length || nx >= data[ny].length || visited[ny][nx]) {
                    continue;
                }

                openNodes.add(new Node2115(nx, ny, r + data[ny][nx]));
                visited[ny][nx] = true;
            }
        }

        System.out.println(r);
    }

    private static String[] getInput() {
        return ("9165985876154699219988192797299938946759548993842382179897895847959995468789384779887969965834298757\n" +
            "9998264799399739852669159899969915752881369928979589425659869512849898348591988899941938989958981368\n" +
            "8899439372928798295981284899995498957198997822776999766989269673341115866632499916582179985999797178\n" +
            "9421373314798816327241583824298987799745811978869899899899999689988933652499891999899718759652641398\n" +
            "9949959989598519929632977671926983547787167773939739198988988851889519228348573848979828599971727943\n" +
            "2989192448779756984992587956689999842746119939998567894718899918799954281648927282191699499764931166\n" +
            "9536499426969676189784443598979559899499773119978188394689557995599331996979736693529419681251956996\n" +
            "1751936936954979779895998917863998697858138691879749999727789764741997539275579593798975539798818972\n" +
            "7274746616894977772987689459988988891794769772199789185982726479992789563994126143759648659653627929\n" +
            "9768211178649269995923493912176968666679999859829913848396992289798892197419967914777851877899477278\n" +
            "9548265381888989898295865115497574817515178999881998168798969539989897979118423366393898169329939816\n" +
            "5346816194398971492996938739782725986596757996472498951666679779287752997484725936582558299359964812\n" +
            "2169889968977588483272986596967998729999883958728879796999737989942928997399898577324799379798165679\n" +
            "5756791285489939716695977945746649394898965799971791856215827997699758829299499895496993375817688889\n" +
            "1189911823962199612255959754716953775367595418858921381973577497988994381699599376948136788285998274\n" +
            "6979939299876267486998569159464199464965974658469723835541132825855912999935783995325776519991867869\n" +
            "7829881839997878488734339898145124968389716637926975397882799631985728918519668146942356911683694975\n" +
            "4973997156985297758563699888285794535932928974966978296562139199889893476728999942416259889912711471\n" +
            "9534797994999788999759474799734844192166842694971197691854895641895188586895455192589986798997913693\n" +
            "2819784988295365719599997589858664998979399989735399884134499895873389479651295996498866989999839974\n" +
            "6129944129969453989699888866976269999128469799982997963676678293639494188967181417362922546475281598\n" +
            "9716591619494494558731969871917781639763288288691962634219628479989988828962651246459129568974491171\n" +
            "7894482759988943587818779691955666934841799931371572868778856815865991124376396974593984947897442991\n" +
            "1858394839149329949299818139987736884848178995897668499996188998667699618978779495959989927996996397\n" +
            "9917119977481935978699197129381282957978683881779735689583195993458959989897975199999285945815997999\n" +
            "1699128644295779552889817864638995225998895287579281281998594696644213479297199692979781937997179875\n" +
            "8289472999575769888986161678994171392789714988719932799199558696689379278929798887977551878788398787\n" +
            "9175199787918919864185866129967892999898318993291829953849829788999738939999469848949591989149962712\n" +
            "6199185639948988878676462732119197225882925435874993975768952939825978994996892549999699984912695356\n" +
            "3396488583898545189449831859997919671368765298895692847986994925119921865818912189499168979958896494\n" +
            "9938398336984195997576689194134985999635325783571526493894394589917988793989755746999698829649589393\n" +
            "6985999497989855798918845568728129349777627978979362788359497952698956998292996918648692699694349887\n" +
            "1983998898992942689898989791697792913121476987631657959299998868769559979688918876893688862439185197\n" +
            "7357776772998794811999929459919571597899775797198394831478974157594549666253368399586349899987699999\n" +
            "7959339994696971922632949191545959757987989979787788169979748972545188912811999284691666989915589771\n" +
            "9759961834557989443271499976572889278289319995879484972649589489199519573998999779383692242897767588\n" +
            "9179641759649515678926897874488858988897787895855979191899776967749898123692484838554413919817918963\n" +
            "6839761379993114699928685489349481791394595895995488717149328139993956495742445794747189959976877649\n" +
            "7719888678899926948979191678165949542927483596385943836398298659699148778343914768999898197267997991\n" +
            "8769727958885176392169934146934172488871783986753744485932511888999799159194751538987186924419884479\n" +
            "8991999992999929999897984599767977994699827768813861298818993288386943869782797583698682188885283399\n" +
            "7618891897968939798498999399278379698962758659288163913969875574557443483997692961986999939899819481\n" +
            "8879168556577788339549879912587944289995777798881688244889799389891799876819699576957557971877898128\n" +
            "9291695492736749179768777989972456198819896897386591181956469899994155718299785968686938589318979479\n" +
            "7795999789358882518386787487224292238877893956695912892699678996798249199324897986249725799479399977\n" +
            "7157587585956489829119899759896997988918879818491999931184192141949199172799356679472486896195864474\n" +
            "8369998998796539973399979987949986863639829688617494995142799799778639869918868177475771699198897648\n" +
            "6597979998999646797717999135983617949772192975367951599248888799957499785994774771558359664883693989\n" +
            "8494399497884369731998936499887638658943929616778716995355827188745777858983426799559721799739362629\n" +
            "7988688889359569698815957687229757873596999199938929146584679599188685439814466838789683219554419476\n" +
            "9247298485768968989794782295879689497761879956994316114992795924987262939579993889586584967699911179\n" +
            "9585318399982677997578979671589977319169586337196999959189521499887711992343851295981988819951696391\n" +
            "9688419987598317988779256198579659993398274893319912676925791429994831998147985462785322692958417991\n" +
            "9162526572947889939619891135679859992512868978725888837198497899147199825914711544192984934699878858\n" +
            "9997992172487298739215471619688889939819838188921969391991648949979166298795194179866892977317488668\n" +
            "4342969513889787361777751972798659462495119377122327857968869995779944989446988786669168689799978198\n" +
            "9991989792887933999782849992885388199539897175961949179928388186994694998865752811971681796835228899\n" +
            "9689884899572994167989994913659819996941825788877898819911788969419991277989227389959829597962878797\n" +
            "9919879157714445923163829999735888889724892834556637899899999974386932299127993655996239789769999544\n" +
            "8158429263197997788393974998999939783878693949897825898975995959979986635699272784592895953298769979\n" +
            "6933156399191389719992767671896649494989689977121886269912939799779176467784638649979932992778611947\n" +
            "9937886748328953482685979758934658999819989845699372899934596991259755741819979779889881397565568579\n" +
            "6628287411551868899648999511774894539834357749799788481594933187788979179772699757813897655969637759\n" +
            "1681758889993977881987844998586378799289969999542735618971489988849699628668119898999679199493949189\n" +
            "7756952966791755589777886975949697297729368719914568929921989979278297994486928996985666791996513546\n" +
            "7959939142776881868999785876799728991368773296952962918971941199687791336941297836969992566198117899\n" +
            "9919799591383641992451999795686291882995991159937526287799259396499782592953878378919512331399769295\n" +
            "9979988697799299993248298599458499952548969526718987894849849961566959196296836979299696595499951269\n" +
            "8989115299788834687596939899183482448889849883899881337161943668283395379982966119363885868799817686\n" +
            "3917979748934696925996487991958936699899582264499928897937594999993391587881813378359971812919779972\n" +
            "7798825938413749319999785941489868587674397449269738978317389938919984622199581498529938819682972471\n" +
            "7749948181969657924978787838596695869966969369158962427829991499668977295896254897998989743989952389\n" +
            "6398998955793159938819887788968371888968395499298999755899949268632898528951445886293997299392969779\n" +
            "9539983773686993498991392899889572558299175987819981797981886747694169489893727157979378595839496298\n" +
            "9599999287193787789729986389617989992374479972959749982818939999895692869845931449861767839389829149\n" +
            "4889176836968695418797941267779399488818276299865898498649489796971967884999112828966259271689857259\n" +
            "7139139187955994831738585298962698949981181887589699969919418116293969946431635243579918986885919619\n" +
            "7935837589876972417397869798312741789988882795986987744696696629647813993997762979599189148298958581\n" +
            "8791284382878199766798982957837371997961791491996313339915819373928948979938596612197699691473969914\n" +
            "1372974376943738718696489825854989786382983999879965536283589995655483569334269859559761199879679291\n" +
            "9719671746885959293824693968698399315424948599622489865269469961617669916732853892695261177787994496\n" +
            "7849319899791243818799879975258532467699699999398269787199341236894749619399991856788917729181194197\n" +
            "9148263323757681139949799197198549259446781912979561927929891898923997238737858757915394778591127667\n" +
            "1945591989843987198391756487965892217958111951642219915715439797998675786222936815998966826782589594\n" +
            "5254778838276178988551266998775337643868756772978159294191819424888551874271745598136847865594999289\n" +
            "3998557499888479397893111869792537426834985941959185929798919875628859952429849886849639853349182496\n" +
            "8958877677623879221669563361998988956937492798913659491897715599879989983979859984661929198589817564\n" +
            "9989969281947994946512996153988898382127889834868897498898867797998892148557494839482788798269898496\n" +
            "9779851919689459958899912662756981619298821434879339749956713988385759469959788279765789991925947918\n" +
            "4196471827218751217999955385399979199189581896987488518319752529829892282899638773984698854832217793\n" +
            "5968399569814182278918739959691249997958587277596698779311494824187981838837892479347738267879912541\n" +
            "8659894118598996577145167494795592898597175979829589886499183913197276893979278479678988218798894991\n" +
            "6649277899864442637895792699719172999998662937819979979769821897696686788719291967114346689819212978\n" +
            "6699889979874997773889958977455779952988466968759738452847569993821963796198741514377779188577811889\n" +
            "1173976886399735895884999486498511998399588328832288555979166889776686858695414825689877994898917949\n" +
            "9629147139882191719857926675389957799296279989442991999622669639755767892922474778888299984452999898\n" +
            "4391879947595258989144156986799899833458894496218777872997391217987623967998848296898891312888421579\n" +
            "9362699299759899879589911968947655251988997896874791778876389919939496782959848979971163281597161779\n" +
            "1652179878596567755189593919723287659886989991199789326995581692999815373488857495418291969165215199\n" +
            "9788389477191759669998798979888832998683445278988415997594187969167388689899997659918914818788982937").split("\n");
    }
}
