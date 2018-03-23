package com.example.studente.subnetting;

/**
 * Created by studente on 26/02/2018.
 */

public class IpAddress {

    private Byte ip[];
    private byte mask;

    public IpAddress(String ip1, String ip2, String ip3, String ip4, String mask)
    {
        ip=new Byte[4];
        ip[0]=(byte)(0x00+Short.valueOf(ip1));
        ip[1]=(byte)(0x00+Short.valueOf(ip2));
        ip[2]=(byte)(0x00+Short.valueOf(ip3));
        ip[3]=(byte)(0x00+Short.valueOf(ip4));
        this.mask=Byte.parseByte(mask);

    }

    public IpAddress(Byte [] ip, byte mask)
    {
        this.ip=ip;
        this.mask=mask;
    }

    public IpAddress(String[] x)
    {
        ip=new Byte[4];
        ip[0]=(byte)(0x00+Short.valueOf(x[0]));
        ip[1]=(byte)(0x00+Short.valueOf(x[1]));
        ip[2]=(byte)(0x00+Short.valueOf(x[2]));
        ip[3]=(byte)(0x00+Short.valueOf(x[3]));
        this.mask=Byte.parseByte(x[4]);
    }

    public String[] toStringArray()
    {
        String array[]=new String[5];
        for(int i=0;i<4;i++)
        {
            array[i]=""+ip[i].intValue();
        }

        array[4]=""+mask;

        return array;
    }

    public String toString()
    {
        String x=new String("");

        x+=""+(short)(0xff & ip[0]);
        x+="."+(short)(0xff & ip[1]);
        x+="."+(short)(0xff & ip[2]);
        x+="."+(short)(0xff & ip[3]);
        x+=" /"+mask;

        return x;
    }

    public Byte[] getIp() {
        return ip;
    }

    public void setIp(Byte[] ip) {
        this.ip = ip;
    }

    public byte getMask() {
        return mask;
    }

    public void setMask(byte mask) {
        this.mask = mask;
    }

    private Byte[] changeMask()
    {
      Byte[] array=new Byte[4];

      array[0]=0;
      array[1]=0;
      array[2]=0;
      array[3]=0;


      for(int i=0;i<mask;i++)
      {
          array[i/8]= (byte) (array[i/8] >> 1);
          array[i/8]= (byte) (array[i/8] | (byte)0x80);
      }

      return array;
    }

    public IpAddress getNetAddress()
    {
        Byte[] result=new Byte[4];

        Byte[] maschera=this.changeMask();

        for(int i=0;i<4;i++)
        {
            result[i]=(byte)(ip[i] & maschera[i]);
        }

        return new IpAddress(result, this.getMask());
    }

    public IpAddress getBroadcastAddress()
    {
        Byte[] result=new Byte[4];

        Byte[] maschera=this.changeMask();

        for(int i=0;i<4;i++)
        {
            result[i]=(byte)(ip[i] | (~ maschera[i]));
        }

        return new IpAddress(result, this.getMask());
    }

    public IpAddress getFirstHost()
    {
        IpAddress base=this.getNetAddress();

        Byte[] change=base.getIp();
        change[3]=(byte)(change[3] + 0x01);
        base.setIp(change);

        return base;
    }

    public IpAddress getLastHost()
    {
        IpAddress base=this.getBroadcastAddress();

        Byte[] change=base.getIp();
        change[3]=(byte)(change[3] - 0x01);
        base.setIp(change);

        return base;
    }

    public static char getNetClass(int ip)
    {

        if(ip<128)
        {
            return 'A';
        }
        else if (ip>=128 && ip<192)
        {
            return 'B';
        }
        else if(ip>=192 && ip<224)
        {
            return 'C';
        }
        else if(ip>=224 && ip<240)
        {
            return 'D';
        }
        else
        {
            return 'E';
        }
    }

}
