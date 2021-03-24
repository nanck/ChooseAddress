## Summary
一个轻量级的，行政区划地址选择器。

## Download
> compile 'com.nanck.ChooseAddress:addresschoose:1.0.0'

## Usage
1.启动 ChooserActivity，第二个参数传 null
```JAVA
    ChooserActivity.start(MainActivity.this, null);
```
2.注册广播，接收选择结果
```JAVA
    ...
    private BroadcastReceiver echoRegionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ChooserActivity.ACTION)) {
                String region = intent.getStringExtra(ChooserActivity.ART_ADDRESS);
                tvEchoRegion.setText(region);
                setSaveTextButtonEnable();
            }
        }
    };

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooserActivity.ACTION);

        registerReceiver(echoRegionReceiver, intentFilter);
    }
```
## Preview
![](https://github.com/nanck/ChooseAddress/blob/master/art/previewgif.gif)

## TODO
1. 顶部导航栏样式适配；
2. 读数据库使用异步方式；


## License
> Copyright (C) 2017 nanck
>
> Copyright (C) 1991, 1999 Free Software Foundation, Inc. 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA Everyone is > > permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.
>
> [This is the first released version of the Lesser GPL. It also counts as the successor of the GNU Library Public License, > > version 2, hence the version number 2.1.]
