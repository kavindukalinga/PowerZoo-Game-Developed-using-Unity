using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class coinUpdaterScript : MonoBehaviour
{
    public TextMeshProUGUI balance;
    public bool isSceneNeedUpdateCoins = true;
    private coinManagerScript coinManager;

    void Start()
    {
        coinManager = GameObject.Find("CoinManagerScript").GetComponent<coinManagerScript>();
    }

    void Update()
    {
        if (isSceneNeedUpdateCoins)
        {
            int coins = coinManager.getCoins();
            balance.text = coins.ToString();
        }
    }
}
