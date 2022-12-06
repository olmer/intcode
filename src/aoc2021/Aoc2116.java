package aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Aoc2116 {
    public static void main(String[] args) {
        for (var inputP : getInput()) {
            var packetAsBits = "";
            for (var c : inputP.toCharArray()) {
                var ns = Integer.toBinaryString(Integer.parseInt(String.valueOf(c), 16));
                packetAsBits += ("0000" + ns).substring(ns.length());
            }

            var packetType = Integer.parseInt(packetAsBits.substring(3, 6), 2);

            if (packetType != 4) {
                var rootPacket = new Operator(packetAsBits);
                System.out.println(rootPacket.calculateValue());
            }
        }
    }

    interface Packet {
        int getSumOfVersions();
        long calculateValue();
    }

    static class Operator implements Packet {
        private final int version;
        private final int typeId;
        private int valueLength = 0;
        private final List<Packet> children;

        public Operator(String bits) {
            this.children = new ArrayList<>();
            this.version = Integer.parseInt(bits.substring(0, 3), 2);
            this.typeId = Integer.parseInt(bits.substring(3, 6), 2);
            var nextSIndex = 7;
            var mode = bits.charAt(6);
            if (mode == '0') {
                var dataLengthLength = 15;
                var dataLength = Integer.parseInt(bits.substring(nextSIndex, nextSIndex + dataLengthLength), 2);
                this.valueLength = nextSIndex + dataLengthLength + dataLength;
                var values = bits.substring(nextSIndex + dataLengthLength, nextSIndex + dataLengthLength + dataLength);
                while (values.length() > 0) {
                    var packetType = Integer.parseInt(values.substring(3, 6), 2);
                    if (packetType == 4) {
                        var newChild = new LiteralValue(values);
                        children.add(newChild);
                        values = values.substring(newChild.getValueLength());
                    } else {
                        var newChild = new Operator(values);
                        children.add(newChild);
                        values = values.substring(newChild.getValueLength());
                    }
                }
            } else {
                var dataLengthLength = 11;
                var dataCount = Integer.parseInt(bits.substring(nextSIndex, nextSIndex + dataLengthLength), 2);
                this.valueLength = nextSIndex + dataLengthLength;
                var values = bits.substring(nextSIndex + dataLengthLength);
                for (var i = dataCount; i > 0; i--) {
                    var packetType = Integer.parseInt(values.substring(3, 6), 2);
                    if (packetType == LiteralValue.TYPE) {
                        var newChild = new LiteralValue(values);
                        children.add(newChild);
                        values = values.substring(newChild.getValueLength());
                        valueLength += newChild.getValueLength();
                    } else {
                        var newChild = new Operator(values);
                        children.add(newChild);
                        values = values.substring(newChild.getValueLength());
                        valueLength += newChild.getValueLength();
                    }
                }
            }
        }

        @Override
        public int getSumOfVersions() {
            return children.stream().map(Packet::getSumOfVersions).reduce(0, Integer::sum);
        }

        public long calculateValue() {
            switch (typeId) {
                case 0:
                    return children.stream().map(Packet::calculateValue).reduce(0L, Long::sum);
                case 1:
                    return children.stream().map(Packet::calculateValue).reduce(1L, (var a, var b) -> a * b);
                case 2:
                    return children.stream().map(Packet::calculateValue).reduce(Long.MAX_VALUE, Math::min);
                case 3:
                    return children.stream().map(Packet::calculateValue).reduce(Long.MIN_VALUE, Math::max);
                case 5:
                    return children.get(0).calculateValue() > children.get(1).calculateValue() ? 1L : 0L;
                case 6:
                    return children.get(0).calculateValue() < children.get(1).calculateValue() ? 1L : 0L;
                case 7:
                    return children.get(0).calculateValue() == children.get(1).calculateValue() ? 1L : 0L;
            }
            return 0L;
        }

        public int getVersion() {
            return version;
        }

        public int getTypeId() {
            return typeId;
        }

        public List<Packet> getChildren() {
            return children;
        }

        public int getValueLength() {
            return valueLength;
        }
    }

    static class LiteralValue implements Packet {
        private static final int TYPE = 4;

        private final int version;
        private final int typeId;
        private final long value;
        private int valueLength = 0;

        public LiteralValue(String bits) {
            this.typeId = 4;
            this.version = Integer.parseInt(bits.substring(0, 3), 2);
            this.value = Long.parseLong(getValueString(bits), 2);
        }

        public int getVersion() {
            return version;
        }

        public int getTypeId() {
            return typeId;
        }

        public long getValue() {
            return value;
        }

        public int getValueLength() {
            return valueLength;
        }

        @Override
        public int getSumOfVersions() {
            return version;
        }

        @Override
        public long calculateValue() {
            return value;
        }

        private String getValueString(String bits) {
            var valueString = "";
            var si = 6;
            while (true) {
                var ts = bits.substring(si, si + 5);
                valueString += ts.substring(1);
                si += 5;
                if (ts.charAt(0) == '0') {
                    valueLength = si;
                    break;
                }
            }
            return valueString;
        }
    }

    private static String[] getInput() {
        return ("6051639005B56008C1D9BB3CC9DAD5BE97A4A9104700AE76E672DC95AAE91425EF6AD8BA5591C00F92073004AC0171007E0BC248BE0008645982B1CA680A7A0CC60096802723C94C265E5B9699E7E94D6070C016958F99AC015100760B45884600087C6E88B091C014959C83E740440209FC89C2896A50765A59CE299F3640D300827902547661964D2239180393AF92A8B28F4401BCC8ED52C01591D7E9D2591D7E9D273005A5D127C99802C095B044D5A19A73DC0E9C553004F000DE953588129E372008F2C0169FDB44FA6C9219803E00085C378891F00010E8FF1AE398803D1BE25C743005A6477801F59CC4FA1F3989F420C0149ED9CF006A000084C5386D1F4401F87310E313804D33B4095AFBED32ABF2CA28007DC9D3D713300524BCA940097CA8A4AF9F4C00F9B6D00088654867A7BC8BCA4829402F9D6895B2E4DF7E373189D9BE6BF86B200B7E3C68021331CD4AE6639A974232008E663C3FE00A4E0949124ED69087A848002749002151561F45B3007218C7A8FE600FC228D50B8C01097EEDD7001CF9DE5C0E62DEB089805330ED30CD3C0D3A3F367A40147E8023221F221531C9681100C717002100B36002A19809D15003900892601F950073630024805F400150D400A70028C00F5002C00252600698400A700326C0E44590039687B313BF669F35C9EF974396EF0A647533F2011B340151007637C46860200D43085712A7E4FE60086003E5234B5A56129C91FC93F1802F12EC01292BD754BCED27B92BD754BCED27B100264C4C40109D578CA600AC9AB5802B238E67495391D5CFC402E8B325C1E86F266F250B77ECC600BE006EE00085C7E8DF044001088E31420BCB08A003A72BF87D7A36C994CE76545030047801539F649BF4DEA52CBCA00B4EF3DE9B9CFEE379F14608").split("\n");
    }
}
