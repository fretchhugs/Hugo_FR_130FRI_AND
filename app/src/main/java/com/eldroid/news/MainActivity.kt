package com.eldroid.news

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val items = listOf(
        NewsItems("New evidence links Alice Guo to Chinese espionage in the Philippines, raising alarms",
            "Raissa Robles\n" +
                    "Published: 12:00pm, 1 Oct 2024\n" +
                    "A high-stakes congressional investigation into a web of criminal enterprises across the Philippines took a dramatic turn with new evidence linking infamous former mayor Alice Guo to Chinese espionage operations, raising alarms about foreign infiltration and election interference.\n" +
                    "Once the mayor of Bamban town, Guo fled to Indonesia amid criminal allegations and questions regarding her citizenship, but was extradited to Manila last month. Now, she faces intense scrutiny from Senator Risa Hontiveros, who is leading the inquiry into criminality linked to the controversial Philippine Offshore Gaming Operators (Pogos).\n" +
                    "During a hearing on Friday, Guo was confronted with an Al Jazeera documentary featuring Cambodian-Chinese betting kingpin She Zhijiang, currently imprisoned in Thailand for running illegal online gambling operations.\n.\n" +
                    "\n",
            R.drawable.gou),
        NewsItems("Vilma Santos, Luis Manzano team up for Batangas governor race",
            "MANILA, Philippines — Veteran actress Vilma Santos-Recto and her son Luis Manzano are teaming up for the gubernatorial race in Batangas for the 2025 midterm elections.\n" +
                    "\n" +
                    "Vilma and her host-actor son Luis filed their certificates of candidacy (COCs) for governor and vice-governor of Batangas, respectively, at the Commission on Elections' office in the Batangas Provincial Capitol.\n" +
                    "\n" +
                    "Ryan Recto, Vilma's son with fellow politician Ralph Recto, also filed his COC to be the Representative of Batangas' 6th district.\n" +
                    "\n" +
                    "Ralph and Luis' wife, Jessy Mendiola, also an actress, accompanied the family in their COC filing.\n" +
                    "\n" +
                    "On their ticket is Mikee Morada, husband of online personality Alex Gonzaga. Morada is running for vice-mayor of Lipa..",
            R.drawable.vilma),
        NewsItems("Vietnam condemns China's 'brutal behavior' in fisher attack",
            "HANOI, Vietnam — Vietnam's foreign ministry condemned on Wednesday China's \"brutal behavior\" during a violent attack on Vietnamese fishers in the disputed South China Sea.\n" +
                    "\n" +
                    "The 10 fishermen were reportedly beaten with iron bars and robbed of thousands of dollars' worth of fish and equipment on Sunday off the Paracel Islands -- an archipelago in the resource-rich waterway claimed by China, Vietnam, the Philippines, Taiwan, Malaysia and Brunei.\n" +
                    "\n" +
                    "Vietnam \"resolutely protests the brutal behavior of Chinese law enforcement forces against Vietnamese fishermen and fishing vessels operating in the Hoang Sa archipelago of Vietnam\", foreign ministry spokesperson Vietnam Pham Thu Hang said in a statement, using the Vietnamese term for the Paracel Islands.\n" +
                    "\n" +
                    "\"The above actions of Chinese law enforcement forces seriously violated Vietnam's sovereignty\" over the archipelago, the statement said.\n" +
                    "\n" +
                    "Four of the Vietnamese crew were taken to hospital on Monday after arriving at Quang Ngai port, according to the state-run Tien Phong newspaper, which said the men were attacked by around 40 people for three hours.\n" +
                    "\n" +
                    "\"Wearing chequered clothes, they cruelly beat us with iron bars,\" captain Nguyen Thanh Bien was quoted as saying, adding that he fell unconscious for around an hour after the attack.\n" +
                    "\n" +
                    "Footage on Tien Phong's website showed the fishers being taken from their boat on stretchers. One had a broken leg and two suffered broken arms, the report said.\n" +
                    "\n" +
                    "Captain Bien told authorities that around \$20,000 worth of equipment and fish had been stolen in the attack..",
            R.drawable.paracel)
    )



    private lateinit var listView: ListView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        listView.adapter = MyListAdapter(items) { item ->
            val fragment = ItemDetailFragment.newInstance(item)

            if (isPortraitMode()) {
                listView.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (isPortraitMode()) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                listView.visibility = View.VISIBLE
                fragmentContainer.visibility = View.GONE
            } else {
                super.onBackPressed()
            }
        } else {

            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun isPortraitMode(): Boolean {
        return resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    }
}