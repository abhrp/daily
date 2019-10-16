package com.abhrp.daily.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.widget.Toolbar

import com.abhrp.daily.R
import com.abhrp.daily.common.util.AppLogger
import com.abhrp.daily.core.util.PixelHelper
import com.abhrp.daily.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject

class NewsDetailActivity : BaseActivity() {

    companion object {

        const val ITEM_ID = "itemId"
        const val TITLE = "headline"
        const val IMAGE_URL = "imageUrl"

        fun newIntent(context: Context, id: String, imageUrl: String, headline: String): Intent {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(ITEM_ID, id)
            intent.putExtra(TITLE, headline)
            intent.putExtra(IMAGE_URL, imageUrl)
            return intent
        }
    }

    @Inject
    lateinit var pixelHelper: PixelHelper
    @Inject
    lateinit var logger: AppLogger

    private var itemId: String = ""
    private var headline: String = ""
    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }
        setValuesFromIntent()

//        val body = "<p>The governor of the Bank of England has unveiled the new £20 polymer banknote featuring the artist JMW Turner as the Bank continues its switch away from paper money.</p> <p>Mark Carney revealed the note’s design in Margate at Turner Contemporary gallery, which is named after the painter. Carney was joined by the artist Tracey Emin, who grew up in the Kent coastal town.</p> <p>Turner, the first artist to feature on a British banknote, went to school in Margate and many of his paintings were of the local seascapes. Turner Contemporary is on the site of the lodging house where he stayed when he returned to the town.</p> <p>The £20 note follows <a href=\"https://www.theguardian.com/business/2016/jun/02/flash-the-plastic-bank-of-england-unveils-untearable-plastic-5-note\">the Bank’s launch of polymer £5 and £10 notes,</a> which went into circulation in September 2016 and September 2017 respectively. The Bank is opting for polymer over traditional paper notes because it is cleaner, longer-lasting and harder to forge.</p> <p>The Bank said the latest note was its most secure yet with two windows and a two-colour foil, making it difficult to counterfeit. With more than 2bn £20 notes in circulation, it is Britain’s most used and forged banknote. </p> <p>The foil features Turner Contemporary, which opened in 2011 and helped revive Margate as an artistic centre, in blue and the town’s lighthouse in gold. The note will enter circulation on 20 February alongside existing paper £20 notes, which will be phased out.</p> <figure class=\"element element-image\" data-media-id=\"a70ca97f97497a11b76265a3f25a0d01f7e98045\"> <img src=\"https://media.guim.co.uk/a70ca97f97497a11b76265a3f25a0d01f7e98045/0_472_2362_1417/1000.jpg\" alt=\"The front of the new £20 banknote.\" width=\"1000\" height=\"600\" class=\"gu-image\"> <figcaption> <span class=\"element-image__caption\">The front of the new £20 banknote.</span> <span class=\"element-image__credit\">Photograph: Bank of England</span> </figcaption> </figure> <p>The new polymer notes feature images of historic British figures, with Winston Churchill appearing on the £5 note and Jane Austen on the £10 note. A <a href=\"https://www.theguardian.com/business/2019/jul/15/alan-turing-to-feature-on-new-50-note\">£50 note featuring the mathematician and second world war codebreaker Alan Turing</a> will follow in 2021.</p> <figure class=\"element element-atom\"> <gu-atom data-atom-id=\"c8dff2aa-feed-43d8-a031-92d9ad58b0e8\" data-atom-type=\"qanda\"> <div> <div class=\"atom-Qanda\"> <p></p> <p>William Shakespeare was the first historical character to appear on a Bank of England note in 1970. Here’s the full list of the <a href=\"https://www.bankofengland.co.uk/banknotes/withdrawn-banknotes\">historical characters</a> that have appeared on <a href=\"https://www.theguardian.com/business/gallery/2013/apr/26/banknotes-winston-churchill-predecessors-in-pictures\">banknotes</a> issued by the central bank in England and Wales over the last five decades.</p> <p><b>Past banknotes<br></b></p> <p><b>1970</b> William Shakespeare, playwright (£20) <br><b>1971</b> Duke of Wellington, soldier and statesman (£5) <br><b>1975</b> Florence Nightingale, founder of modern nursing (£10) <br><b>1978</b> Sir Isaac Newton, physicist and mathematician (£1) <br><b>1981</b> Sir Christopher Wren, architect (£50) <br><b>1990</b> George Stephenson, engineer (£5) <br><b>1991</b> Michael Faraday, scientist (£20) <br><b>1992</b> Charles Dickens, author (£10) <br><b>1994</b> Sir John Houblon, first Bank of England governor (£50) <br><b>1999</b> Sir Edward Elgar, composer (£20) <br><b>2000</b> Charles Darwin, naturalist (£10)<br><b>2002</b> Elizabeth Fry, prison reformer (£5)</p> <p><b>Current banknotes</b></p> <p><b>2007</b> Adam Smith, economist (£20)<br><b>2011</b> Matthew Boulton and James Watt, steam engine industrialists (£50)<br><b>2016</b> Winston Churchill, prime minister (£5)<br><b>2017</b> Jane Austen, author (£10)</p> <p><b>Future banknotes</b></p> <p><b>2020</b> JMW Turner, artist (£20)<br><b>2021</b> Alan Turing, mathematician (£50)</p> <p><b>Julia Kollewe</b><br></p> <p></p> </div> </div> </gu-atom> </figure> <p>Carney said on Thursday: “Our banknotes celebrate the UK’s heritage, salute its culture, and testify to the achievements of its most notable individuals. Turner’s contribution to art extends well beyond his favourite stretch of shoreline.</p> <p>“Turner’s painting was transformative, his influence spanned lifetimes, and his legacy endures today. The new £20 note celebrates Turner, his art and his legacy in all their radiant, colourful, evocative glory.”</p> <p>The note features Turner’s 1799 self-portrait, which hangs in Tate Britain, as well as one of his most recognisable works, The Fighting Temeraire – a tribute to the ship which played a big part in Nelson’s victory at the battle of Trafalgar in 1805.</p> <figure class=\"element element-embed\" data-alt=\"Sign up to the daily Business Today email\"> <iframe src=\"https://www.theguardian.com/email/form/plaintone/3887\" height=\"52px\" data-form-title=\"Sign up for Business Today\" data-form-description=\"Get the headlines and editors' picks every weekday morning.\" scrolling=\"no\" seamless frameborder=\"0\" class=\"iframed--overflow-hidden email-sub__iframe js-email-sub__iframe js-email-sub__iframe--article\" data-form-success-desc=\"Thanks for signing up\"></iframe> <figcaption> Sign up to the daily Business Today email or follow Guardian Business on Twitter at @BusinessDesk </figcaption> </figure> <p>The £20 note has a quote from Turner, “light is therefore colour”, and his signature taken from his will, which bequeathed many of his works to the nation.</p> <p>Emin said: “What is really fantastic about Turner being on this note is he was wild and he was a maverick. This note is going to go all over the world and wherever I go I can say: ‘This is where I come from.’ Not just Margate but art. I come from art.”</p> <p>She said if Turner Contemporary had been open when she grew up in Margate her younger years would have been transformed.</p> <p>“If this building had been here and I could have come and looked at art and thought I could be part of something, my adolescent life would have been very different. I might not have been as successful but it would have been very different.”</p> <p><a href=\"https://www.theguardian.com/artanddesign/2016/apr/22/jmw-turner-face-next-20-note-painter-british-banknote\">Turner was selected in 2016</a> from a longlist of 590 painters, sculptors, fashion designers, photographers, film-makers and actors put forward by 30,000 members of the public. The list was whittled down to five and Turner was chosen by the Bank’s banknote character advisory committee.</p> <p>The new £20 note will be the first to feature the signature of Sarah John, the Bank’s chief cashier. She said: “The new £20 is an important part of our commitment to providing banknotes that people can use with confidence. With the £20 being our most common this marks a big step forward in our fight against counterfeiting.”</p>"
//        val html = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY)
//        } else {
//            Html.fromHtml(body)
//        }
//        newsBody.text = html


    }

    private fun setValuesFromIntent() {
        imageUrl = intent.getStringExtra(IMAGE_URL) ?: ""
        itemId = intent.getStringExtra(ITEM_ID) ?: ""
        headline = intent.getStringExtra(TITLE) ?: ""
    }


    override fun online() {

    }

    override fun offline() {

    }
}
