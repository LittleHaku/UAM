<?xml version="1.0" encoding="UTF-8"?>
<drawing version="7">
    <attr value="spartan3" name="DeviceFamilyName">
        <trait delete="all:0" />
        <trait editname="all:0" />
        <trait edittrait="all:0" />
    </attr>
    <netlist>
        <signal name="CLR" />
        <signal name="CLK" />
        <signal name="R" />
        <signal name="XLXN_14" />
        <signal name="M" />
        <signal name="U" />
        <signal name="D" />
        <signal name="XLXN_27" />
        <signal name="XLXN_31" />
        <signal name="XLXN_38" />
        <signal name="XLXN_39" />
        <signal name="XLXN_41" />
        <signal name="XLXN_42" />
        <signal name="XLXN_43" />
        <signal name="XLXN_44" />
        <signal name="XLXN_45" />
        <signal name="XLXN_46" />
        <signal name="XLXN_47" />
        <signal name="XLXN_48" />
        <signal name="XLXN_58" />
        <port polarity="Input" name="CLR" />
        <port polarity="Input" name="CLK" />
        <port polarity="Output" name="R" />
        <port polarity="Output" name="M" />
        <port polarity="Input" name="U" />
        <port polarity="Input" name="D" />
        <blockdef name="fdc">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-32" y2="-32" x1="0" />
            <line x2="64" y1="-256" y2="-256" x1="0" />
            <line x2="320" y1="-256" y2="-256" x1="384" />
            <rect width="256" x="64" y="-320" height="256" />
            <line x2="80" y1="-112" y2="-128" x1="64" />
            <line x2="64" y1="-128" y2="-144" x1="80" />
            <line x2="192" y1="-64" y2="-32" x1="192" />
            <line x2="64" y1="-32" y2="-32" x1="192" />
        </blockdef>
        <blockdef name="inv">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-32" y2="-32" x1="0" />
            <line x2="160" y1="-32" y2="-32" x1="224" />
            <line x2="128" y1="-64" y2="-32" x1="64" />
            <line x2="64" y1="-32" y2="0" x1="128" />
            <line x2="64" y1="0" y2="-64" x1="64" />
            <circle r="16" cx="144" cy="-32" />
        </blockdef>
        <blockdef name="and3">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="192" y1="-128" y2="-128" x1="256" />
            <line x2="144" y1="-176" y2="-176" x1="64" />
            <line x2="64" y1="-80" y2="-80" x1="144" />
            <arc ex="144" ey="-176" sx="144" sy="-80" r="48" cx="144" cy="-128" />
            <line x2="64" y1="-64" y2="-192" x1="64" />
        </blockdef>
        <blockdef name="and4">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-112" y2="-112" x1="144" />
            <arc ex="144" ey="-208" sx="144" sy="-112" r="48" cx="144" cy="-160" />
            <line x2="144" y1="-208" y2="-208" x1="64" />
            <line x2="64" y1="-64" y2="-256" x1="64" />
            <line x2="192" y1="-160" y2="-160" x1="256" />
            <line x2="64" y1="-256" y2="-256" x1="0" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-64" y2="-64" x1="0" />
        </blockdef>
        <blockdef name="or3">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="48" y1="-64" y2="-64" x1="0" />
            <line x2="72" y1="-128" y2="-128" x1="0" />
            <line x2="48" y1="-192" y2="-192" x1="0" />
            <line x2="192" y1="-128" y2="-128" x1="256" />
            <arc ex="192" ey="-128" sx="112" sy="-80" r="88" cx="116" cy="-168" />
            <arc ex="48" ey="-176" sx="48" sy="-80" r="56" cx="16" cy="-128" />
            <line x2="48" y1="-64" y2="-80" x1="48" />
            <line x2="48" y1="-192" y2="-176" x1="48" />
            <line x2="48" y1="-80" y2="-80" x1="112" />
            <arc ex="112" ey="-176" sx="192" sy="-128" r="88" cx="116" cy="-88" />
            <line x2="48" y1="-176" y2="-176" x1="112" />
        </blockdef>
        <blockdef name="or4">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="48" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="48" y1="-256" y2="-256" x1="0" />
            <line x2="192" y1="-160" y2="-160" x1="256" />
            <arc ex="112" ey="-208" sx="192" sy="-160" r="88" cx="116" cy="-120" />
            <line x2="48" y1="-208" y2="-208" x1="112" />
            <line x2="48" y1="-112" y2="-112" x1="112" />
            <line x2="48" y1="-256" y2="-208" x1="48" />
            <line x2="48" y1="-64" y2="-112" x1="48" />
            <arc ex="48" ey="-208" sx="48" sy="-112" r="56" cx="16" cy="-160" />
            <arc ex="192" ey="-160" sx="112" sy="-112" r="88" cx="116" cy="-200" />
        </blockdef>
        <block symbolname="inv" name="XLXI_5">
            <blockpin signalname="XLXN_39" name="I" />
            <blockpin signalname="R" name="O" />
        </block>
        <block symbolname="or3" name="XLXI_13">
            <blockpin signalname="M" name="I0" />
            <blockpin signalname="D" name="I1" />
            <blockpin signalname="U" name="I2" />
            <blockpin signalname="XLXN_46" name="O" />
        </block>
        <block symbolname="or3" name="XLXI_14">
            <blockpin signalname="XLXN_31" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="XLXN_38" name="I2" />
            <blockpin signalname="XLXN_47" name="O" />
        </block>
        <block symbolname="or3" name="XLXI_15">
            <blockpin signalname="R" name="I0" />
            <blockpin signalname="D" name="I1" />
            <blockpin signalname="M" name="I2" />
            <blockpin signalname="XLXN_48" name="O" />
        </block>
        <block symbolname="or4" name="XLXI_16">
            <blockpin signalname="XLXN_45" name="I0" />
            <blockpin signalname="XLXN_44" name="I1" />
            <blockpin signalname="XLXN_43" name="I2" />
            <blockpin signalname="XLXN_42" name="I3" />
            <blockpin signalname="XLXN_27" name="O" />
        </block>
        <block symbolname="and3" name="XLXI_9">
            <blockpin signalname="M" name="I0" />
            <blockpin signalname="U" name="I1" />
            <blockpin signalname="D" name="I2" />
            <blockpin signalname="XLXN_43" name="O" />
        </block>
        <block symbolname="and3" name="XLXI_10">
            <blockpin signalname="XLXN_39" name="I0" />
            <blockpin signalname="XLXN_31" name="I1" />
            <blockpin signalname="D" name="I2" />
            <blockpin signalname="XLXN_44" name="O" />
        </block>
        <block symbolname="and3" name="XLXI_11">
            <blockpin signalname="M" name="I0" />
            <blockpin signalname="R" name="I1" />
            <blockpin signalname="U" name="I2" />
            <blockpin signalname="XLXN_45" name="O" />
        </block>
        <block symbolname="and4" name="XLXI_12">
            <blockpin signalname="XLXN_38" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="U" name="I2" />
            <blockpin signalname="XLXN_41" name="I3" />
            <blockpin signalname="XLXN_42" name="O" />
        </block>
        <block symbolname="and3" name="XLXI_8">
            <blockpin signalname="XLXN_48" name="I0" />
            <blockpin signalname="XLXN_47" name="I1" />
            <blockpin signalname="XLXN_46" name="I2" />
            <blockpin signalname="XLXN_14" name="O" />
        </block>
        <block symbolname="fdc" name="XLXI_3">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_14" name="D" />
            <blockpin signalname="XLXN_39" name="Q" />
        </block>
        <block symbolname="fdc" name="XLXI_4">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_27" name="D" />
            <blockpin signalname="M" name="Q" />
        </block>
        <block symbolname="inv" name="XLXI_26">
            <blockpin signalname="U" name="I" />
            <blockpin signalname="XLXN_31" name="O" />
        </block>
        <block symbolname="inv" name="XLXI_27">
            <blockpin signalname="D" name="I" />
            <blockpin signalname="XLXN_41" name="O" />
        </block>
        <block symbolname="inv" name="XLXI_28">
            <blockpin signalname="M" name="I" />
            <blockpin signalname="XLXN_38" name="O" />
        </block>
    </netlist>
    <sheet sheetnum="1" width="3520" height="2720">
        <branch name="CLK">
            <wire x2="304" y1="2128" y2="2128" x1="176" />
            <wire x2="1024" y1="2128" y2="2128" x1="304" />
            <wire x2="1488" y1="1104" y2="1104" x1="304" />
            <wire x2="304" y1="1104" y2="2128" x1="304" />
            <wire x2="1024" y1="1696" y2="2128" x1="1024" />
            <wire x2="1568" y1="1696" y2="1696" x1="1024" />
        </branch>
        <branch name="R">
            <wire x2="752" y1="1952" y2="2016" x1="752" />
            <wire x2="2576" y1="2016" y2="2016" x1="752" />
            <wire x2="1072" y1="128" y2="272" x1="1072" />
            <wire x2="1136" y1="272" y2="272" x1="1072" />
            <wire x2="2576" y1="128" y2="128" x1="1072" />
            <wire x2="2576" y1="128" y2="1520" x1="2576" />
            <wire x2="2720" y1="1520" y2="1520" x1="2576" />
            <wire x2="2576" y1="1520" y2="2016" x1="2576" />
            <wire x2="2576" y1="1520" y2="1520" x1="2528" />
        </branch>
        <iomarker fontsize="28" x="2720" y="1520" name="R" orien="R0" />
        <iomarker fontsize="28" x="176" y="2128" name="CLK" orien="R180" />
        <iomarker fontsize="28" x="176" y="2224" name="CLR" orien="R180" />
        <branch name="CLR">
            <wire x2="368" y1="2224" y2="2224" x1="176" />
            <wire x2="1568" y1="2224" y2="2224" x1="368" />
            <wire x2="1488" y1="1200" y2="1200" x1="368" />
            <wire x2="368" y1="1200" y2="2224" x1="368" />
            <wire x2="1568" y1="1792" y2="2224" x1="1568" />
        </branch>
        <instance x="752" y="608" name="XLXI_9" orien="R0" />
        <instance x="1200" y="1648" name="XLXI_8" orien="R0" />
        <instance x="2304" y="1552" name="XLXI_5" orien="R0" />
        <branch name="M">
            <wire x2="720" y1="1312" y2="1504" x1="720" />
            <wire x2="784" y1="1504" y2="1504" x1="720" />
            <wire x2="720" y1="1504" y2="1824" x1="720" />
            <wire x2="752" y1="1824" y2="1824" x1="720" />
            <wire x2="1952" y1="1312" y2="1312" x1="720" />
            <wire x2="752" y1="544" y2="688" x1="752" />
            <wire x2="1136" y1="688" y2="688" x1="752" />
            <wire x2="1584" y1="688" y2="688" x1="1136" />
            <wire x2="1952" y1="688" y2="688" x1="1584" />
            <wire x2="1952" y1="688" y2="976" x1="1952" />
            <wire x2="2720" y1="976" y2="976" x1="1952" />
            <wire x2="1952" y1="976" y2="1312" x1="1952" />
            <wire x2="1136" y1="336" y2="688" x1="1136" />
            <wire x2="1584" y1="592" y2="592" x1="1536" />
            <wire x2="1584" y1="592" y2="688" x1="1584" />
            <wire x2="1952" y1="976" y2="976" x1="1872" />
        </branch>
        <instance x="1488" y="1232" name="XLXI_4" orien="R0" />
        <iomarker fontsize="28" x="2720" y="976" name="M" orien="R0" />
        <branch name="U">
            <wire x2="304" y1="960" y2="960" x1="192" />
            <wire x2="784" y1="960" y2="960" x1="304" />
            <wire x2="784" y1="960" y2="1376" x1="784" />
            <wire x2="256" y1="112" y2="112" x1="208" />
            <wire x2="208" y1="112" y2="480" x1="208" />
            <wire x2="320" y1="480" y2="480" x1="208" />
            <wire x2="752" y1="480" y2="480" x1="320" />
            <wire x2="320" y1="480" y2="496" x1="320" />
            <wire x2="496" y1="496" y2="496" x1="320" />
            <wire x2="320" y1="496" y2="496" x1="304" />
            <wire x2="304" y1="496" y2="832" x1="304" />
            <wire x2="304" y1="832" y2="848" x1="304" />
            <wire x2="304" y1="848" y2="960" x1="304" />
            <wire x2="384" y1="832" y2="832" x1="304" />
            <wire x2="496" y1="208" y2="496" x1="496" />
            <wire x2="1136" y1="208" y2="208" x1="496" />
        </branch>
        <branch name="D">
            <wire x2="272" y1="1024" y2="1024" x1="192" />
            <wire x2="272" y1="1024" y2="1040" x1="272" />
            <wire x2="320" y1="1040" y2="1040" x1="272" />
            <wire x2="272" y1="1040" y2="1440" x1="272" />
            <wire x2="784" y1="1440" y2="1440" x1="272" />
            <wire x2="272" y1="1440" y2="1888" x1="272" />
            <wire x2="752" y1="1888" y2="1888" x1="272" />
            <wire x2="752" y1="416" y2="416" x1="272" />
            <wire x2="272" y1="416" y2="704" x1="272" />
            <wire x2="272" y1="704" y2="720" x1="272" />
            <wire x2="272" y1="720" y2="1024" x1="272" />
            <wire x2="736" y1="704" y2="704" x1="272" />
        </branch>
        <iomarker fontsize="28" x="192" y="960" name="U" orien="R180" />
        <iomarker fontsize="28" x="192" y="1024" name="D" orien="R180" />
        <instance x="1200" y="1136" name="XLXI_16" orien="R0" />
        <branch name="XLXN_27">
            <wire x2="1488" y1="976" y2="976" x1="1456" />
        </branch>
        <instance x="320" y="1072" name="XLXI_27" orien="R0" />
        <instance x="384" y="864" name="XLXI_26" orien="R0" />
        <instance x="736" y="896" name="XLXI_10" orien="R0" />
        <branch name="XLXN_31">
            <wire x2="624" y1="832" y2="832" x1="608" />
            <wire x2="624" y1="832" y2="1728" x1="624" />
            <wire x2="752" y1="1728" y2="1728" x1="624" />
            <wire x2="736" y1="768" y2="768" x1="624" />
            <wire x2="624" y1="768" y2="832" x1="624" />
        </branch>
        <instance x="1136" y="400" name="XLXI_11" orien="R0" />
        <instance x="256" y="304" name="XLXI_12" orien="R0" />
        <instance x="1536" y="560" name="XLXI_28" orien="R180" />
        <branch name="XLXN_38">
            <wire x2="256" y1="240" y2="592" x1="256" />
            <wire x2="608" y1="592" y2="592" x1="256" />
            <wire x2="1312" y1="592" y2="592" x1="608" />
            <wire x2="608" y1="576" y2="576" x1="288" />
            <wire x2="608" y1="576" y2="592" x1="608" />
            <wire x2="288" y1="576" y2="1600" x1="288" />
            <wire x2="752" y1="1600" y2="1600" x1="288" />
        </branch>
        <branch name="XLXN_41">
            <wire x2="704" y1="16" y2="16" x1="256" />
            <wire x2="704" y1="16" y2="1040" x1="704" />
            <wire x2="256" y1="16" y2="32" x1="256" />
            <wire x2="256" y1="32" y2="48" x1="256" />
            <wire x2="560" y1="1040" y2="1040" x1="544" />
            <wire x2="704" y1="1040" y2="1040" x1="560" />
        </branch>
        <branch name="XLXN_42">
            <wire x2="592" y1="144" y2="144" x1="512" />
            <wire x2="592" y1="144" y2="400" x1="592" />
            <wire x2="1200" y1="400" y2="400" x1="592" />
            <wire x2="1200" y1="400" y2="880" x1="1200" />
        </branch>
        <branch name="XLXN_43">
            <wire x2="1104" y1="480" y2="480" x1="1008" />
            <wire x2="1104" y1="480" y2="944" x1="1104" />
            <wire x2="1200" y1="944" y2="944" x1="1104" />
        </branch>
        <branch name="XLXN_44">
            <wire x2="1088" y1="768" y2="768" x1="992" />
            <wire x2="1088" y1="768" y2="1008" x1="1088" />
            <wire x2="1200" y1="1008" y2="1008" x1="1088" />
        </branch>
        <branch name="XLXN_45">
            <wire x2="1200" y1="1072" y2="1264" x1="1200" />
            <wire x2="1936" y1="1264" y2="1264" x1="1200" />
            <wire x2="1936" y1="272" y2="272" x1="1392" />
            <wire x2="1936" y1="272" y2="1264" x1="1936" />
        </branch>
        <instance x="784" y="1568" name="XLXI_13" orien="R0" />
        <instance x="752" y="1792" name="XLXI_14" orien="R0" />
        <instance x="752" y="2016" name="XLXI_15" orien="R0" />
        <branch name="XLXN_46">
            <wire x2="1152" y1="1440" y2="1440" x1="1040" />
            <wire x2="1152" y1="1440" y2="1456" x1="1152" />
            <wire x2="1200" y1="1456" y2="1456" x1="1152" />
        </branch>
        <branch name="XLXN_47">
            <wire x2="1104" y1="1664" y2="1664" x1="1008" />
            <wire x2="1104" y1="1520" y2="1664" x1="1104" />
            <wire x2="1200" y1="1520" y2="1520" x1="1104" />
        </branch>
        <branch name="XLXN_48">
            <wire x2="1200" y1="1888" y2="1888" x1="1008" />
            <wire x2="1200" y1="1584" y2="1888" x1="1200" />
        </branch>
        <instance x="1568" y="1824" name="XLXI_3" orien="R0" />
        <branch name="XLXN_14">
            <wire x2="1472" y1="1520" y2="1520" x1="1456" />
            <wire x2="1472" y1="1520" y2="1568" x1="1472" />
            <wire x2="1568" y1="1568" y2="1568" x1="1472" />
        </branch>
        <branch name="XLXN_39">
            <wire x2="256" y1="176" y2="176" x1="224" />
            <wire x2="224" y1="176" y2="1296" x1="224" />
            <wire x2="736" y1="1296" y2="1296" x1="224" />
            <wire x2="2016" y1="1296" y2="1296" x1="736" />
            <wire x2="2016" y1="1296" y2="1520" x1="2016" />
            <wire x2="2304" y1="1520" y2="1520" x1="2016" />
            <wire x2="2016" y1="1520" y2="1808" x1="2016" />
            <wire x2="752" y1="1664" y2="1664" x1="688" />
            <wire x2="688" y1="1664" y2="1808" x1="688" />
            <wire x2="2016" y1="1808" y2="1808" x1="688" />
            <wire x2="736" y1="832" y2="1296" x1="736" />
            <wire x2="1984" y1="1568" y2="1568" x1="1952" />
            <wire x2="1984" y1="1520" y2="1568" x1="1984" />
            <wire x2="2016" y1="1520" y2="1520" x1="1984" />
        </branch>
    </sheet>
</drawing>