//初始化各个城市
var citysFlight=new Array();
citysFlight[0]=new Array('36','安庆','anqing','anqing');
citysFlight[1]=new Array('37','蚌埠','bangbu','bangbu');
citysFlight[2]=new Array('38','巢湖','chaohu','chaohu');
citysFlight[3]=new Array('39','池州','chizhou','chizhou');
citysFlight[4]=new Array('40','滁州','chuzhou','chuzhou');
citysFlight[5]=new Array('41','阜阳','fuyang','fuyang');
citysFlight[6]=new Array('42','合肥','hefei','hefei');
citysFlight[7]=new Array('43','淮北','huaibei','huaibei');
citysFlight[8]=new Array('44','淮南','huainan','huainan');
citysFlight[9]=new Array('45','黄山','huangshan','huangshan');
citysFlight[10]=new Array('46','六安','liuan','liuan');
citysFlight[11]=new Array('47','马鞍山','maanshan','maanshan');
citysFlight[12]=new Array('48','宿州','suzhouu','suzhouu');
citysFlight[13]=new Array('49','铜陵','tongling','tongling');
citysFlight[14]=new Array('50','芜湖','wuhu','wuhu');
citysFlight[15]=new Array('51','宣城','xuancheng','xuancheng');
citysFlight[16]=new Array('52','亳州','bozhou','bozhou');
citysFlight[17]=new Array('53','北京','beijing','beijing');
citysFlight[18]=new Array('54','福州','fuzhou','fuzhou');
citysFlight[19]=new Array('55','龙岩','longyan','longyan');
citysFlight[20]=new Array('56','南平','nanping','nanping');
citysFlight[21]=new Array('57','宁德','ningde','ningde');
citysFlight[22]=new Array('58','莆田','putian','putian');
citysFlight[23]=new Array('59','泉州','quanzhou','quanzhou');
citysFlight[24]=new Array('60','三明','sanming','sanming');
citysFlight[25]=new Array('61','厦门','xiamen','xiamen');
citysFlight[26]=new Array('62','漳州','zhangzhou','zhangzhou');
citysFlight[27]=new Array('63','白银','baiyin','baiyin');
citysFlight[28]=new Array('64','定西','dingxi','dingxi');
citysFlight[29]=new Array('65','甘南','gannan','gannan');
citysFlight[30]=new Array('66','嘉峪关','jiayuguan','jiayuguan');
citysFlight[31]=new Array('67','金昌','jinchang','jinchang');
citysFlight[32]=new Array('68','酒泉','jiuquan','jiuquan');
citysFlight[33]=new Array('69','兰州','lanzhou','lanzhou');
citysFlight[34]=new Array('70','临夏','linxia','linxia');
citysFlight[35]=new Array('71','陇南','longnan','longnan');
citysFlight[36]=new Array('72','平凉','pingliang','pingliang');
citysFlight[37]=new Array('73','庆阳','qingyang','qingyang');
citysFlight[38]=new Array('74','天水','tianshui','tianshui');
citysFlight[39]=new Array('75','武威','wuwei','wuwei');
citysFlight[40]=new Array('76','张掖','zhangye','zhangye');
citysFlight[41]=new Array('77','潮州','chaozhou','chaozhou');
citysFlight[42]=new Array('78','东莞','dongguan','dongguan');
citysFlight[43]=new Array('79','佛山','foshan','foshan');
citysFlight[44]=new Array('80','广州','guangzhou','guangzhou');
citysFlight[45]=new Array('81','河源','heyuan','heyuan');
citysFlight[46]=new Array('82','惠州','huizhou','huizhou');
citysFlight[47]=new Array('83','江门','jiangmen','jiangmen');
citysFlight[48]=new Array('84','揭阳','jieyang','jieyang');
citysFlight[49]=new Array('85','茂名','maoming','maoming');
citysFlight[50]=new Array('86','梅州','meizhou','meizhou');
citysFlight[51]=new Array('87','清远','qingyuan','qingyuan');
citysFlight[52]=new Array('88','汕头','shantou','shantou');
citysFlight[53]=new Array('89','汕尾','shanwei','shanwei');
citysFlight[54]=new Array('90','韶关','shaoguan','shaoguan');
citysFlight[55]=new Array('91','深圳','shenzhen','shenzhen');
citysFlight[56]=new Array('92','阳江','yangjiang','yangjiang');
citysFlight[57]=new Array('93','云浮','yunfu','yunfu');
citysFlight[58]=new Array('94','湛江','zhanjiang','zhanjiang');
citysFlight[59]=new Array('95','肇庆','zhaoqing','zhaoqing');
citysFlight[60]=new Array('96','中山','zhongshan','zhongshan');
citysFlight[61]=new Array('97','珠海','zhuhai','zhuhai');
citysFlight[62]=new Array('98','百色','baise','baise');
citysFlight[63]=new Array('99','北海','beihai','beihai');
citysFlight[64]=new Array('100','崇左','chongzuo','chongzuo');
citysFlight[65]=new Array('101','防城港','fangchenggang','fangchenggang');
citysFlight[66]=new Array('102','桂林','guilin','guilin');
citysFlight[67]=new Array('103','贵港','guigang','guigang');
citysFlight[68]=new Array('104','河池','hechi','hechi');
citysFlight[69]=new Array('105','贺州','hezhou','hezhou');
citysFlight[70]=new Array('106','来宾','laibin','laibin');
citysFlight[71]=new Array('107','柳州','liuzhou','liuzhou');
citysFlight[72]=new Array('108','南宁','nanning','nanning');
citysFlight[73]=new Array('109','钦州','qinzhou','qinzhou');
citysFlight[74]=new Array('110','梧州','wuzhou','wuzhou');
citysFlight[75]=new Array('111','玉林','yulin','yulin');
citysFlight[76]=new Array('112','安顺','anshun','anshun');
citysFlight[77]=new Array('113','毕节','bijie','bijie');
citysFlight[78]=new Array('114','贵阳','guiyang','guiyang');
citysFlight[79]=new Array('115','六盘水','liupanshui','liupanshui');
citysFlight[80]=new Array('116','黔东','qiandongnanmiaozudongzuzizhizhou','qiandongnanmiaozudongzuzizhizhou');
citysFlight[81]=new Array('117','黔南','qiannanbuyizumiaozuzizhizhou','qiannanbuyizumiaozuzizhizhou');
citysFlight[82]=new Array('118','黔西','qianxinanbuyizumiaozuzizhizhou','qianxinanbuyizumiaozuzizhizhou');
citysFlight[83]=new Array('119','铜仁','tongren','tongren');
citysFlight[84]=new Array('120','遵义','zunyi','zunyi');
citysFlight[85]=new Array('121','白沙','baishalizuzizhixian','baishalizuzizhixian');
citysFlight[86]=new Array('122','保亭','baotinglizumiaozuzizhixian','baotinglizumiaozuzizhixian');
citysFlight[87]=new Array('123','昌江','changjianglizuzizhixian','changjianglizuzizhixian');
citysFlight[88]=new Array('124','澄迈县','chengmaixian','chengmaixian');
citysFlight[89]=new Array('125','定安县','dinganxian','dinganxian');
citysFlight[90]=new Array('126','东方','dongfang','dongfang');
citysFlight[91]=new Array('127','海口','haikou','haikou');
citysFlight[92]=new Array('128','乐东','ledonglizuzizhixian','ledonglizuzizhixian');
citysFlight[93]=new Array('129','临高县','lingaoxian','lingaoxian');
citysFlight[94]=new Array('130','陵水','lingshui','lingshui');
citysFlight[95]=new Array('131','琼海','qionghai','qionghai');
citysFlight[96]=new Array('132','琼中','qiongzhonglizumiaozuzizhixian','qiongzhonglizumiaozuzizhixian');
citysFlight[97]=new Array('133','三亚','sanya','sanya');
citysFlight[98]=new Array('134','屯昌县','tunchangxian','tunchangxian');
citysFlight[99]=new Array('135','万宁','wanning','wanning');
citysFlight[100]=new Array('136','文昌','wenchang','wenchang');
citysFlight[101]=new Array('137','五指山','wuzhishan','wuzhishan');
citysFlight[102]=new Array('138','儋州','danzhou','danzhou');
citysFlight[103]=new Array('139','保定','baoding','baoding');
citysFlight[104]=new Array('140','沧州','cangzhou','cangzhou');
citysFlight[105]=new Array('141','承德','chengde','chengde');
citysFlight[106]=new Array('142','邯郸','handan','handan');
citysFlight[107]=new Array('143','衡水','hengshui','hengshui');
citysFlight[108]=new Array('144','廊坊','langfang','langfang');
citysFlight[109]=new Array('145','秦皇岛','qinhuangdao','qinhuangdao');
citysFlight[110]=new Array('146','石家庄','shijiazhuang','shijiazhuang');
citysFlight[111]=new Array('147','唐山','tangshan','tangshan');
citysFlight[112]=new Array('148','邢台','xingtai','xingtai');
citysFlight[113]=new Array('149','张家口','zhangjiakou','zhangjiakou');
citysFlight[114]=new Array('150','安阳','anyang','anyang');
citysFlight[115]=new Array('151','鹤壁','hebi','hebi');
citysFlight[116]=new Array('152','济源','jiyuan','jiyuan');
citysFlight[117]=new Array('153','焦作','jiaozuo','jiaozuo');
citysFlight[118]=new Array('154','开封','kaifeng','kaifeng');
citysFlight[119]=new Array('155','洛阳','luoyang','luoyang');
citysFlight[120]=new Array('156','南阳','nanyang','nanyang');
citysFlight[121]=new Array('157','平顶山','pingdingshan','pingdingshan');
citysFlight[122]=new Array('158','三门峡','sanmenxia','sanmenxia');
citysFlight[123]=new Array('159','商丘','shangqiu','shangqiu');
citysFlight[124]=new Array('160','新乡','xinxiang','xinxiang');
citysFlight[125]=new Array('161','信阳','xinyang','xinyang');
citysFlight[126]=new Array('162','许昌','xuchang','xuchang');
citysFlight[127]=new Array('163','郑州','zhengzhou','zhengzhou');
citysFlight[128]=new Array('164','周口','zhoukou','zhoukou');
citysFlight[129]=new Array('165','驻马店','zhumadian','zhumadian');
citysFlight[130]=new Array('166','漯河','luohe','luohe');
citysFlight[131]=new Array('167','濮阳','puyang','puyang');
citysFlight[132]=new Array('168','大庆','daqing','daqing');
citysFlight[133]=new Array('169','大兴安岭','daxinganling','daxinganling');
citysFlight[134]=new Array('170','哈尔滨','haerbin','haerbin');
citysFlight[135]=new Array('171','鹤岗','hegang','hegang');
citysFlight[136]=new Array('172','黑河','heihe','heihe');
citysFlight[137]=new Array('173','鸡西','jixi','jixi');
citysFlight[138]=new Array('174','佳木斯','jiamusi','jiamusi');
citysFlight[139]=new Array('175','牡丹江','mudanjiang','mudanjiang');
citysFlight[140]=new Array('176','七台河','qitaihe','qitaihe');
citysFlight[141]=new Array('177','齐齐哈尔','qiqihaer','qiqihaer');
citysFlight[142]=new Array('178','双鸭山','shuangyashan','shuangyashan');
citysFlight[143]=new Array('179','绥化','suihua','suihua');
citysFlight[144]=new Array('180','伊春','yichun','yichun');
citysFlight[145]=new Array('181','鄂州','ezhou','ezhou');
citysFlight[146]=new Array('182','恩施','enshitujiazumiaozuzizhizhou','enshitujiazumiaozuzizhizhou');
citysFlight[147]=new Array('183','黄冈','huanggang','huanggang');
citysFlight[148]=new Array('184','黄石','huangshi','huangshi');
citysFlight[149]=new Array('185','荆门','jingmen','jingmen');
citysFlight[150]=new Array('186','荆州','jingzhou','jingzhou');
citysFlight[151]=new Array('187','潜江','qianjiang','qianjiang');
citysFlight[152]=new Array('188','神农架','shennongjialinqu','shennongjialinqu');
citysFlight[153]=new Array('189','十堰','shiyan','shiyan');
citysFlight[154]=new Array('190','随州','suizhou','suizhou');
citysFlight[155]=new Array('191','天门','tianmen','tianmen');
citysFlight[156]=new Array('192','武汉','wuhan','wuhan');
citysFlight[157]=new Array('193','仙桃','xiantao','xiantao');
citysFlight[158]=new Array('194','咸宁','xianning','xianning');
citysFlight[159]=new Array('195','襄樊','xiangfan','xiangfan');
citysFlight[160]=new Array('196','孝感','xiaogan','xiaogan');
citysFlight[161]=new Array('197','宜昌','yichang','yichang');
citysFlight[162]=new Array('198','常德','changde','changde');
citysFlight[163]=new Array('199','长沙','changsha','changsha');
citysFlight[164]=new Array('200','郴州','chenzhou','chenzhou');
citysFlight[165]=new Array('201','衡阳','hengyang','hengyang');
citysFlight[166]=new Array('202','怀化','huaihua','huaihua');
citysFlight[167]=new Array('203','娄底','loudi','loudi');
citysFlight[168]=new Array('204','邵阳','shaoyang','shaoyang');
citysFlight[169]=new Array('205','湘潭','xiangtan','xiangtan');
citysFlight[170]=new Array('206','湘西','xiangxi','xiangxi');
citysFlight[171]=new Array('207','益阳','yiyang','yiyang');
citysFlight[172]=new Array('208','永州','yongzhou','yongzhou');
citysFlight[173]=new Array('209','岳阳','yueyang','yueyang');
citysFlight[174]=new Array('210','张家界','zhangjiajie','zhangjiajie');
citysFlight[175]=new Array('211','株洲','zhuzhou','zhuzhou');
citysFlight[176]=new Array('212','白城','baicheng','baicheng');
citysFlight[177]=new Array('213','白山','baishan','baishan');
citysFlight[178]=new Array('214','长春','changchun','changchun');
citysFlight[179]=new Array('215','吉林','jilin','jilin');
citysFlight[180]=new Array('216','辽源','liaoyuan','liaoyuan');
citysFlight[181]=new Array('217','四平','siping','siping');
citysFlight[182]=new Array('218','松原','songyuan','songyuan');
citysFlight[183]=new Array('219','通化','tonghua','tonghua');
citysFlight[184]=new Array('220','延边','yanbian','yanbian');
citysFlight[185]=new Array('221','常州','changzhou','changzhou');
citysFlight[186]=new Array('222','淮安','huaian','huaian');
citysFlight[187]=new Array('223','连云港','lianyungang','lianyungang');
citysFlight[188]=new Array('224','南京','nanjing','nanjing');
citysFlight[189]=new Array('225','南通','nantong','nantong');
citysFlight[190]=new Array('226','苏州','suzhou','suzhou');
citysFlight[191]=new Array('227','宿迁','suqian','suqian');
citysFlight[192]=new Array('228','泰州','taizhou','taizhou');
citysFlight[193]=new Array('229','无锡','wuxi','wuxi');
citysFlight[194]=new Array('230','徐州','xuzhou','xuzhou');
citysFlight[195]=new Array('231','盐城','yancheng','yancheng');
citysFlight[196]=new Array('232','扬州','yangzhou','yangzhou');
citysFlight[197]=new Array('233','镇江','zhenjiang','zhenjiang');
citysFlight[198]=new Array('234','抚州','fuzhou','fuzhou');
citysFlight[199]=new Array('235','赣州','ganzhou','ganzhou');
citysFlight[200]=new Array('236','吉安','jian','jian');
citysFlight[201]=new Array('237','景德镇','jingdezhen','jingdezhen');
citysFlight[202]=new Array('238','九江','jiujiang','jiujiang');
citysFlight[203]=new Array('239','南昌','nanchang','nanchang');
citysFlight[204]=new Array('240','萍乡','pingxiang','pingxiang');
citysFlight[205]=new Array('241','上饶','shangrao','shangrao');
citysFlight[206]=new Array('242','新余','xinyu','xinyu');
citysFlight[207]=new Array('243','宜春','yichun','yichun');
citysFlight[208]=new Array('244','鹰潭','yingtan','yingtan');
citysFlight[209]=new Array('245','鞍山','anshan','anshan');
citysFlight[210]=new Array('246','本溪','benxi','benxi');
citysFlight[211]=new Array('247','朝阳','chaoyang','chaoyang');
citysFlight[212]=new Array('248','大连','dalian','dalian');
citysFlight[213]=new Array('249','丹东','dandong','dandong');
citysFlight[214]=new Array('250','抚顺','fushun','fushun');
citysFlight[215]=new Array('251','阜新','fuxin','fuxin');
citysFlight[216]=new Array('252','葫芦岛','huludao','huludao');
citysFlight[217]=new Array('253','锦州','jinzhou','jinzhou');
citysFlight[218]=new Array('254','辽阳','liaoyang','liaoyang');
citysFlight[219]=new Array('255','盘锦','panjin','panjin');
citysFlight[220]=new Array('256','沈阳','shenyang','shenyang');
citysFlight[221]=new Array('257','铁岭','tieling','tieling');
citysFlight[222]=new Array('258','营口','yingkou','yingkou');
citysFlight[223]=new Array('259','阿拉善盟','alashanmeng','alashanmeng');
citysFlight[224]=new Array('260','巴彦淖尔','bayannaoer','bayannaoer');
citysFlight[225]=new Array('261','包头','baotou','baotou');
citysFlight[226]=new Array('262','赤峰','chifeng','chifeng');
citysFlight[227]=new Array('263','鄂尔多斯','eerduosi','eerduosi');
citysFlight[228]=new Array('264','呼和浩特','huhehaote','huhehaote');
citysFlight[229]=new Array('265','呼伦贝尔','hulunbeier','hulunbeier');
citysFlight[230]=new Array('266','通辽','tongliao','tongliao');
citysFlight[231]=new Array('267','乌海','wuhai','wuhai');
citysFlight[232]=new Array('268','乌兰察布','wulanchabushi','wulanchabushi');
citysFlight[233]=new Array('269','锡林郭勒','xilinguole','xilinguole');
citysFlight[234]=new Array('270','兴安盟','xinganmeng','xinganmeng');
citysFlight[235]=new Array('271','固原','guyuan','guyuan');
citysFlight[236]=new Array('272','石嘴山','shizuishan','shizuishan');
citysFlight[237]=new Array('273','吴忠','wuzhong','wuzhong');
citysFlight[238]=new Array('274','银川','yinchuan','yinchuan');
citysFlight[239]=new Array('275','果洛','guoluocangzuzizhizhou','guoluocangzuzizhizhou');
citysFlight[240]=new Array('276','海北','haibeicangzuzizhizhou','haibeicangzuzizhizhou');
citysFlight[241]=new Array('277','海东','haidong','haidong');
citysFlight[242]=new Array('278','海南','hainancangzuzizhizhou','hainancangzuzizhizhou');
citysFlight[243]=new Array('279','海西','haixi','haixi');
citysFlight[244]=new Array('280','黄南','huangnancangzuzizhizhou','huangnancangzuzizhizhou');
citysFlight[245]=new Array('281','西宁','xining','xining');
citysFlight[246]=new Array('282','玉树','yushucangzuzizhizhou','yushucangzuzizhizhou');
citysFlight[247]=new Array('283','滨州','binzhou','binzhou');
citysFlight[248]=new Array('284','德州','dezhou','dezhou');
citysFlight[249]=new Array('285','东营','dongying','dongying');
citysFlight[250]=new Array('286','菏泽','heze','heze');
citysFlight[251]=new Array('287','济南','jinan','jinan');
citysFlight[252]=new Array('288','济宁','jining','jining');
citysFlight[253]=new Array('289','莱芜','laiwu','laiwu');
citysFlight[254]=new Array('290','聊城','liaocheng','liaocheng');
citysFlight[255]=new Array('291','临沂','linyi','linyi');
citysFlight[256]=new Array('292','青岛','qingdao','qingdao');
citysFlight[257]=new Array('293','日照','rizhao','rizhao');
citysFlight[258]=new Array('294','泰安','taian','taian');
citysFlight[259]=new Array('295','威海','weihai','weihai');
citysFlight[260]=new Array('296','潍坊','weifang','weifang');
citysFlight[261]=new Array('297','烟台','yantai','yantai');
citysFlight[262]=new Array('298','枣庄','zaozhuang','zaozhuang');
citysFlight[263]=new Array('299','淄博','zibo','zibo');
citysFlight[264]=new Array('300','长治','changzhi','changzhi');
citysFlight[265]=new Array('301','大同','datong','datong');
citysFlight[266]=new Array('302','晋城','jincheng','jincheng');
citysFlight[267]=new Array('303','晋中','jinzhong','jinzhong');
citysFlight[268]=new Array('304','临汾','linfen','linfen');
citysFlight[269]=new Array('305','吕梁','lvliang','lvliang');
citysFlight[270]=new Array('306','朔州','shuozhou','shuozhou');
citysFlight[271]=new Array('307','太原','taiyuan','taiyuan');
citysFlight[272]=new Array('308','忻州','xinzhou','xinzhou');
citysFlight[273]=new Array('309','阳泉','yangquan','yangquan');
citysFlight[274]=new Array('310','运城','yuncheng','yuncheng');
citysFlight[275]=new Array('311','安康','ankang','ankang');
citysFlight[276]=new Array('312','宝鸡','baoji','baoji');
citysFlight[277]=new Array('313','汉中','hanzhong','hanzhong');
citysFlight[278]=new Array('314','商洛','shangluo','shangluo');
citysFlight[279]=new Array('315','铜川','tongchuan','tongchuan');
citysFlight[280]=new Array('316','渭南','weinan','weinan');
citysFlight[281]=new Array('317','西安','xian','xian');
citysFlight[282]=new Array('318','咸阳','xianyang','xianyang');
citysFlight[283]=new Array('319','延安','yanan','yanan');
citysFlight[284]=new Array('320','榆林','yulin','yulin');
citysFlight[285]=new Array('321','上海','shanghai','shanghai');
citysFlight[286]=new Array('322','阿坝','abacangzuqiangzuzizhizhou','abacangzuqiangzuzizhizhou');
citysFlight[287]=new Array('323','巴中','bazhong','bazhong');
citysFlight[288]=new Array('324','成都','chengdu','chengdu');
citysFlight[289]=new Array('325','达州','dazhou','dazhou');
citysFlight[290]=new Array('326','德阳','deyang','deyang');
citysFlight[291]=new Array('327','甘孜','ganzi','ganzi');
citysFlight[292]=new Array('328','广安','guangan','guangan');
citysFlight[293]=new Array('329','广元','guangyuan','guangyuan');
citysFlight[294]=new Array('330','乐山','leshan','leshan');
citysFlight[295]=new Array('331','凉山','liangshan','liangshan');
citysFlight[296]=new Array('332','眉山','meishan','meishan');
citysFlight[297]=new Array('333','绵阳','mianyang','mianyang');
citysFlight[298]=new Array('334','南充','nanchong','nanchong');
citysFlight[299]=new Array('335','内江','neijiang','neijiang');
citysFlight[300]=new Array('336','攀枝花','panzhihua','panzhihua');
citysFlight[301]=new Array('337','遂宁','suining','suining');
citysFlight[302]=new Array('338','雅安','yaan','yaan');
citysFlight[303]=new Array('339','宜宾','yibin','yibin');
citysFlight[304]=new Array('340','资阳','ziyang','ziyang');
citysFlight[305]=new Array('341','自贡','zigong','zigong');
citysFlight[306]=new Array('342','泸州','luzhou','luzhou');
citysFlight[307]=new Array('343','天津','tianjin','tianjin');
citysFlight[308]=new Array('344','阿里','ali','ali');
citysFlight[309]=new Array('345','昌都','changdu','changdu');
citysFlight[310]=new Array('346','拉萨','lasa','lasa');
citysFlight[311]=new Array('347','林芝','linzhi','linzhi');
citysFlight[312]=new Array('348','那曲','naqu','naqu');
citysFlight[313]=new Array('349','日喀则','rikaze','rikaze');
citysFlight[314]=new Array('350','山南','shannan','shannan');
citysFlight[315]=new Array('351','阿克苏','akesu','akesu');
citysFlight[316]=new Array('352','阿拉尔','alaer','alaer');
citysFlight[317]=new Array('353','巴音郭楞','bayinguoleng','bayinguoleng');
citysFlight[318]=new Array('354','博尔塔拉','boertalamengguzizhizhou','boertalamengguzizhizhou');
citysFlight[319]=new Array('355','昌吉','changjihuizuzizhizhou','changjihuizuzizhizhou');
citysFlight[320]=new Array('356','哈密','hami','hami');
citysFlight[321]=new Array('357','和田','hetian','hetian');
citysFlight[322]=new Array('358','喀什','kashi','kashi');
citysFlight[323]=new Array('359','克拉玛依','kelamayi','kelamayi');
citysFlight[324]=new Array('360','克孜','kezilesukeerkezizizhizhou','kezilesukeerkezizizhizhou');
citysFlight[325]=new Array('361','石河子','shihezi','shihezi');
citysFlight[326]=new Array('362','图木舒克','tumushuke','tumushuke');
citysFlight[327]=new Array('363','吐鲁番','tulufan','tulufan');
citysFlight[328]=new Array('364','乌鲁木齐','wulumuqi','wulumuqi');
citysFlight[329]=new Array('365','五家渠','wujiaqu','wujiaqu');
citysFlight[330]=new Array('366','伊犁','yili','yili');
citysFlight[331]=new Array('367','保山','baoshan','baoshan');
citysFlight[332]=new Array('368','楚雄','chuxiongyizuzizhizhou','chuxiongyizuzizhizhou');
citysFlight[333]=new Array('369','大理','dali','dali');
citysFlight[334]=new Array('370','德宏','dehongdaizujingpozuzizhizhou','dehongdaizujingpozuzizhizhou');
citysFlight[335]=new Array('371','迪庆','diqing','diqing');
citysFlight[336]=new Array('372','红河','honghehanizuyizuzizhizhou','honghehanizuyizuzizhizhou');
citysFlight[337]=new Array('373','昆明','kunming','kunming');
citysFlight[338]=new Array('374','丽江','lijiang','lijiang');
citysFlight[339]=new Array('375','临沧','lincang','lincang');
citysFlight[340]=new Array('376','怒江','nujianglilizuzizhizhou','nujianglilizuzizhizhou');
citysFlight[341]=new Array('377','曲靖','qujing','qujing');
citysFlight[342]=new Array('378','思茅','simao','simao');
citysFlight[343]=new Array('379','文山','wenshanzhuangzumiaozuzizhizhou','wenshanzhuangzumiaozuzizhizhou');
citysFlight[344]=new Array('380','西双版纳','xishuangbanna','xishuangbanna');
citysFlight[345]=new Array('381','玉溪','yuxi','yuxi');
citysFlight[346]=new Array('382','昭通','zhaotong','zhaotong');
citysFlight[347]=new Array('383','杭州','hangzhou','hangzhou');
citysFlight[348]=new Array('384','湖州','huzhou','huzhou');
citysFlight[349]=new Array('385','嘉兴','jiaxing','jiaxing');
citysFlight[350]=new Array('386','金华','jinhua','jinhua');
citysFlight[351]=new Array('387','丽水','lishui','lishui');
citysFlight[352]=new Array('388','宁波','ningbo','ningbo');
citysFlight[353]=new Array('389','绍兴','shaoxing','shaoxing');
citysFlight[354]=new Array('390','台州','taizhou','taizhou');
citysFlight[355]=new Array('391','温州','wenzhou','wenzhou');
citysFlight[356]=new Array('392','舟山','zhoushan','zhoushan');
citysFlight[357]=new Array('393','衢州','quzhou','quzhou');
citysFlight[358]=new Array('394','重庆','chongqing','chongqing');
citysFlight[359]=new Array('395','香港','xianggang','xianggang');
citysFlight[360]=new Array('396','澳门','aomen','aomen');
citysFlight[361]=new Array('397','高雄','gaoxiong','gaoxiong');
citysFlight[362]=new Array('398','花莲','hualian','hualian');
citysFlight[363]=new Array('399','基隆','jilong','jilong');
citysFlight[364]=new Array('400','嘉义','jiayi','jiayi');
citysFlight[365]=new Array('401','台北','taibei','taibei');
citysFlight[366]=new Array('402','台东','taidong','taidong');
citysFlight[367]=new Array('403','台南','tainan','tainan');
citysFlight[368]=new Array('404','台中','taizhong','taizhong');
citysFlight[369]=new Array('3105','中卫','zhongwei','zhongwei');
citysFlight[370]=new Array('3113','塔城','tacheng','tacheng');
citysFlight[371]=new Array('3114','阿勒泰','aletai','aletai');
citysFlight[372]=new Array('3143','乌苏里江','wusulijiang','wusulijiang');
citysFlight[373]=new Array('3280','赤壁市','chibishi','chibishi');
citysFlight[374]=new Array('3281','顺德','shunde','shunde');


//国际始发
/*citysFlight[41]=new Array('icn','首尔','shouer','se');
citysFlight[42]=new Array('cju','济州','jizhou','jz');
citysFlight[43]=new Array('pus','釜山','fushan','fs');
citysFlight[44]=new Array('tae','大邱','daqiu','dq');
citysFlight[45]=new Array('del','新德里','xindeli','xdl');
citysFlight[46]=new Array('kix','大阪','daban','db');
citysFlight[47]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[48]=new Array('nrt','东京','dongjing','dj');
citysFlight[49]=new Array('fuk','福冈','fugang','fg');

citysFlight[141]=new Array('icn','首尔','shouer','se');
citysFlight[142]=new Array('cju','济州','jizhou','jz');
citysFlight[143]=new Array('pus','釜山','fushan','fs');
citysFlight[144]=new Array('tae','大邱','daqiu','dq');
citysFlight[145]=new Array('del','新德里','xindeli','xdl');
citysFlight[146]=new Array('kix','大阪','daban','db');
citysFlight[147]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[148]=new Array('nrt','东京','dongjing','dj');
citysFlight[149]=new Array('fuk','福冈','fugang','fg');

citysFlight[241]=new Array('icn','首尔','shouer','se');
citysFlight[242]=new Array('cju','济州','jizhou','jz');
citysFlight[43]=new Array('pus','釜山','fushan','fs');
citysFlight[244]=new Array('tae','大邱','daqiu','dq');
citysFlight[245]=new Array('del','新德里','xindeli','xdl');
citysFlight[246]=new Array('kix','大阪','daban','db');
citysFlight[247]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[248]=new Array('nrt','东京','dongjing','dj');
citysFlight[249]=new Array('fuk','福冈','fugang','fg');

citysFlight[341]=new Array('icn','首尔','shouer','se');
citysFlight[342]=new Array('cju','济州','jizhou','jz');
citysFlight[343]=new Array('pus','釜山','fushan','fs');
citysFlight[344]=new Array('tae','大邱','daqiu','dq');
citysFlight[345]=new Array('del','新德里','xindeli','xdl');
citysFlight[346]=new Array('kix','大阪','daban','db');
citysFlight[347]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[348]=new Array('nrt','东京','dongjing','dj');
citysFlight[349]=new Array('fuk','福冈','fugang','fg');

citysFlight[41]=new Array('icn','首尔','shouer','se');
citysFlight[42]=new Array('cju','济州','jizhou','jz');
citysFlight[43]=new Array('pus','釜山','fushan','fs');
citysFlight[44]=new Array('tae','大邱','daqiu','dq');
citysFlight[45]=new Array('del','新德里','xindeli','xdl');
citysFlight[46]=new Array('kix','大阪','daban','db');
citysFlight[47]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[48]=new Array('nrt','东京','dongjing','dj');
citysFlight[49]=new Array('fuk','福冈','fugang','fg');

citysFlight[41]=new Array('icn','首尔','shouer','se');
citysFlight[42]=new Array('cju','济州','jizhou','jz');
citysFlight[43]=new Array('pus','釜山','fushan','fs');
citysFlight[44]=new Array('tae','大邱','daqiu','dq');
citysFlight[45]=new Array('del','新德里','xindeli','xdl');
citysFlight[46]=new Array('kix','大阪','daban','db');
citysFlight[47]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlight[48]=new Array('nrt','东京','dongjing','dj');
citysFlight[49]=new Array('fuk','福冈','fugang','fg');*/

//初始化国际城市
/*var citysFlightTo=new Array();
citysFlightTo[0]=new Array('HKG','香港','xianggang','xg');
citysFlightTo[1]=new Array('TPE','台北','taibei','tb');	
citysFlightTo[2]=new Array('SIN','新加坡','xinjiapo','xjp');	
citysFlightTo[3]=new Array('BKK','曼谷','mangu','mg');		
citysFlightTo[4]=new Array('JKT','雅加达','yajiada','yjd');
citysFlightTo[5]=new Array('KUL','吉隆坡','jilongpo','jlp');	
citysFlightTo[6]=new Array('REP','暹粒','xianli','xl');		
citysFlightTo[7]=new Array('PNH','金边','jinbian','jb');		
citysFlightTo[8]=new Array('KTM','加德满都','jiademandu','jdmd');			
citysFlightTo[9]=new Array('SGN','胡志明','huzhiming','hzm');			
citysFlightTo[10]=new Array('HAN','河内','henei','hl');		
citysFlightTo[11]=new Array('MNL','马尼拉','manila','man');		
citysFlightTo[12]=new Array('RGN','仰光','yanguang','yg');			
citysFlightTo[13]=new Array('PEN','槟城','bincheng','bc');		
citysFlightTo[14]=new Array('MLE','马累','male','ml');			
citysFlightTo[15]=new Array('DEL','新德里','xindeli','xdl');				
citysFlightTo[16]=new Array('NRT','东京','dongjing','dj');	
citysFlightTo[17]=new Array('KIX','大阪','daban','db');		
citysFlightTo[18]=new Array('NGO','名古屋','mingguwu','mgw');		
citysFlightTo[19]=new Array('fuk','福冈','fugang','fg');	
citysFlightTo[20]=new Array('cju','济州','jizhou','jz');
citysFlightTo[21]=new Array('del','新德里','xindeli','xdl');
citysFlightTo[23]=new Array('ngo','名古屋','mingguwu','mgw');
citysFlightTo[24]=new Array('nrt','东京','dongjing','dj');
citysFlightTo[25]=new Array('KIJ','新泻','xinxie','xx');
citysFlightTo[26]=new Array('TOY','富山','fushan','fs');	
citysFlightTo[27]=new Array('HIJ','广岛','guangdao','gd');		
citysFlightTo[28]=new Array('SDJ','仙台','xiantai','xt');			
citysFlightTo[29]=new Array('CTS','札幌','zhahuang','zh');		
citysFlightTo[30]=new Array('icn','首尔','shouer','se');	
citysFlightTo[31]=new Array('pus','釜山','fushan','fs');	
citysFlightTo[32]=new Array('tae','大邱','daqiu','dq');	
citysFlightTo[33]=new Array('HKG','迪拜','dibai','db');			
citysFlightTo[34]=new Array('ALA','阿拉木图','alamitu','almt');				
citysFlightTo[35]=new Array('IKA','德黑兰','deheilan','dhl');					
citysFlightTo[36]=new Array('ISB','伊斯兰堡','yisilanbao','yslb');							
citysFlightTo[37]=new Array('FRU','比什凯克','bierkaike','bekk');							
citysFlightTo[38]=new Array('ASB','阿什哈巴德','ashenhabade','ashbd');	
citysFlightTo[39]=new Array('BAK','巴库','baku','bk');								
citysFlightTo[40]=new Array('VVO','海参崴','haishenwai','hsw');								
citysFlightTo[41]=new Array('OVB','新西伯利亚','xinxiboliya','xxbly');								
citysFlightTo[42]=new Array('DYU','杜尚别','dushangbie','dsb');								
citysFlightTo[43]=new Array('KHV','哈巴罗夫斯克','habaluofusike','hblfsk');								
citysFlightTo[44]=new Array('OSS','奥什','aoshen','as');								
citysFlightTo[45]=new Array('TAS','塔什干','tashengan','tsg');								
citysFlightTo[46]=new Array('JED','吉达','jida','jd');								
citysFlightTo[47]=new Array('DAC','达卡','daka','dk');								
citysFlightTo[48]=new Array('MEL','墨尔本','moerben','meb');		
citysFlightTo[49]=new Array('SYD','悉尼','xini','xn');		
citysFlightTo[50]=new Array('BNE','布里斯班','bulisiban','blsb');		
citysFlightTo[51]=new Array('AKL','奥克兰','aokelan','akl');		
citysFlightTo[52]=new Array('CDG','巴黎','bali','bl');			
citysFlightTo[53]=new Array('AMS','阿姆斯特丹','amusitedan','amstd');
citysFlightTo[54]=new Array('LAX','洛杉矶','luoshanji','lsj');		
citysFlightTo[55]=new Array('SVO','莫斯科','mosike','msk');			
citysFlightTo[56]=new Array('YVR','温哥华','wengehua','wgh');	*/

var labelFromcity = new Array();
labelFromcity ['热门城市'] = new Array(17,285,188,134,251,25,44,55);
labelFromcity ['A-F'] = new Array(0,1,2,3,4,5,16,17,18,27,28,41,42,43,62,63,64,65,76,77,85,86,87,88,89,90,102,103,104,105,114,132,133,145,146,162,163,164,176,177,178,185,198,209,210,211,212,213,214,215,223,224,225,226,227,247,248,249,264,265,275,276,286,287,288,289,290,308,309,315,316,317,318,319,331,332,333,334,335,358,360,371,373);
labelFromcity ['G-J'] = new Array(6,7,8,9,29,30,31,32,44,45,46,47,48,66,67,68,69,78,91,106,107,115,116,117,134,135,136,137,138,147,148,149,150,165,166,179,186,199,200,201,202,216,217,228,229,235,239,240,241,242,243,244,250,251,252,266,267,277,291,292,293,320,321,336,347,348,349,350,361,362,363,364);
labelFromcity ['K-N'] = new Array(10,11,19,20,21,33,34,35,49,50,70,71,72,79,92,93,94,108,118,119,120,130,139,167,180,187,188,189,203,218,253,254,255,268,269,294,295,296,297,298,299,306,310,311,312,322,323,324,337,338,339,340,351,352);
labelFromcity ['P-W'] = new Array(12,13,14,22,23,24,36,37,38,39,51,52,53,54,55,73,74,80,81,82,83,95,96,97,98,99,100,101,109,110,111,121,122,123,131,140,141,142,143,151,152,153,154,155,156,168,181,182,183,190,191,192,193,204,205,219,220,221,230,231,232,236,237,256,257,258,259,260,270,271,278,279,280,285,300,301,307,313,314,325,326,327,328,329,341,342,343,353,354,355,357,365,366,367,368,370,372,374);
labelFromcity ['X-Z'] = new Array(15,25,26,40,56,57,58,59,60,61,75,84,112,113,124,125,126,127,128,129,144,157,158,159,160,161,169,170,171,172,173,174,175,184,194,195,196,197,206,207,208,222,233,234,238,245,246,261,262,263,272,273,274,281,282,283,284,302,303,304,305,330,344,345,346,356,359,369);
/*labelFromcity ['国际城市'] = new Array(41,42,43,44,45,46,47,48,49);*/
/*var hotList = new Array(53);*/

function getCityId(cityName){
	//'3281','顺德','shunde','shunde'
	var cityId = null;
	for (var i = 0; i < citysFlight.length; i++) {
		var temp = citysFlight[i];
		//var cityInfo = temp.split(",");
		if(temp[1]==cityName){
			cityId = temp[0];
			break;
		}
	}
	return cityId;
}