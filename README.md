# GDX-LAZY-FONT
智能且懒加载的BitmapFont模块。<br/>
Auto generate & manage your bitmapfont without pre-generate.

[Introduce & demo in English ,click me.](#what--how-to)

# 下载地址-Download
[点我下载最新版，click me to download last Releases](https://github.com/dingjibang/GDX-LAZY-FONT/releases/)
<br/>

# 介绍 & 使用方法
这是[LibGDX](https://github.com/libgdx/libgdx/)的一个第三方小组件，他轻巧的只有140行不到的代码，但或许能帮助你大忙( •́ .̫ •̀ )

## 介绍
众所周知，LibGDX的文字模块对非英语地区支持的并不是很好（虽然现在已经好多了）。<br/>
这个LazyFont项目以前就有了，只不过以前是针对LibGDX 1.5版本以下的，因为我自己写的项目也只是用1.4.1版本的，所以这个git项目创建了之后就没太更新过。
前两天痛下狠心把1.4.1版本升级到了最新的1.7.0（当然来自未来的你那时候的GDX版本可能更高了），然后你懂的各种报错红叉叉让我痛不欲生(╯￣Д￣)╯<br/>
其中最大的问题就是文字模块，1.4和1.7的GDX中，文字核心绘制方法已经变得很多了，我以前写的一些中文绘制辅助类也早就用不了了，于是打算重新研究下这个项目。<br/>

废话少说，本项目的作用& 好处有什么？<br/>

1.全自动的生成BitmapFont，无需手动generate，适合大量文字应用的场景（服务器公告、文字冒险游戏等），当然相对的，本组件的性能肯定没有静态生成贴图的原版BitmapFont高（也没少多少），要懒还是要性能全靠你选择。<br/>
(那啥，总有人问我，上面说的那个“性能”到底影不影响，我只想说，可以告诉你，用了这个组件，只会多出连0.1%的CPU占用都不到而已，请不要成天跟我性能性能的了，你特么做个消灭星星也要性能，我也是笑的前列腺液都出来了）<br/>
2.支持BitmapFont.draw()，也支持所有需要调用font的地方（如Scene2d.ui.Label这个类）。<br/>
3.不污染代码，即插即用，理论来说以后的GDX只要更新不是太猛，基本都能兼容（不兼容1.4以下版本，如果你想要1.4以下的，看一下本项目的历史记录自己找吧）。<br/>


## 使用方法
你可以直接下载jar包，然后导入到你的项目里，或者拷贝源代码。<br/>


要创建一个LazyBitmapFont，你需要一个FreeTypeFontGenerator，且只需要一个，你可以创建一个FreeTypeFontGenerator变量用到游戏结束。

    lazyBitmapFont = new LazyBitmapFont(FreeTypeFontGenerator generator, int fontSize);
    
其中，generator就是上面说的变量，你只需要全局new一个这个变量，就可以给所有的LazyBitmapFont或者你自己的需求使用了。
然后，fontSize顾名思义，就是文字的大小了。遗憾的是，本组件不能动态的更改文字大小，也就是说一旦new了一个本组件，就只能使用一种文字大小了，如果你需要好几种字号，那么就分别多new几个不同字号的本组件吧（每个组件都很轻量，不会占资源的~

接着，没了，是的，没了( •́ .̫ •̀ )

你可以直接调用下面代码试一下：

    lazyBitmapFont.draw(batch, "我随便说一句话就能够组成十五字", 100, 200);
    
看看，屏幕上有没有字出来呢？

同理，你也可以弄一个label试试：

    //create...
    LabelStyle style = new LabelStyle();
    style.font = lazyBitmapFont;
    
    Label label = new Label("我就随便打句话",style);
    
    Stage stage = new Stage();
    stage.addActor(label);
    
    // render...
    stage.act();
    stage.draw();
    
    //or change text at runtime...
    label.setText("在运行时候随便的更改下文字！");
    
好了，就是这么简单，除了new的时候和原版不同以外，其他都是一模一样的，而且再也不用换一句文本就重新new一个了，就是这么酸爽！

当然，最后用完了也别忘了dispose()掉哦。

    lazyBitmapFont.dispose();

下面是一模一样的英文版介绍，不用看了，赶紧去试试吧。



# <span id="1">what & how to</span>

## what's this?

LazyBitmapFont is a auto and self-gengrate bitmapFont extend, you can draw any text without generator. And it's helpful for no English-base language to manage BitmapFont.

# how to use it

First, download jar file and import to your project, or copy source.

You need a *FreeTypeFontGenerator* to create this extend, like:

    lazyBitmapFont = new LazyBitmapFont(FreeTypeFontGenerator generator, int fontSize);
    
the *fontSize* is final variable that you can't modify at runtime. Of course you can create some different size object.

Then ,this API is same as BitmapFont.

    lazyBitmapFont.draw(batch, "我随便说一句话就能够组成十五字", 100, 200);
    
or create a Label as usual as before:

    //create...
    LabelStyle style = new LabelStyle();
    style.font = lazyBitmapFont;
    
    Label label = new Label("我就随便打句话",style);
    
    Stage stage = new Stage();
    stage.addActor(label);
    
    // render...
    stage.act();
    stage.draw();
    
    //or change text at runtime...
    label.setText("在运行时候随便的更改下文字！");
    
final, use *dispose()* to dispose this.

     lazyBitmapFont.dispose();

