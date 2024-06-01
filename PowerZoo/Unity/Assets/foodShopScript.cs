using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using System;

public class foodShopScript : MonoBehaviour
{
    public GameObject confirmPopup;
    public GameObject insufficientCoinsPopup;
    // public coinManagerScript coinManager;
    public inventoryScript.Foods foods;
    // public Button buyButton;
    // public Button cancelButton;
    // private Button hayButton;
    // private Button grassButton;
    // private Button meatButton;
    // private Button fishButton;
    // private Button bananaButton;
    // private Button milkButton;
    // private Button bambooButton;
    // private Button honeyButton;
    // private Button magicSpellButton;
    public TextMeshProUGUI foodName;
    public TextMeshProUGUI foodPrice;
    public TextMeshProUGUI balance;
    private int hayPrize = 2;
    private int grassPrize = 2;
    private int meatPrize = 5;
    private int fishPrize = 3;
    private int bananaPrize = 1;
    private int milkPrize = 3;
    private int bambooPrize = 3;
    private int honeyPrize = 4;
    private int magicSpellPrize = 30;
    private int currentFoodId;
    private int currentFoodPrice;

    // void Start() {
    //     DateTime last_logging = new DateTime(2024, 5, 28);
    //     DateTime current_logging = DateTime.Now;
    //     TimeSpan diff = current_logging - last_logging;
    //     int diff_in_days = diff.Days;
    //     Debug.Log("Days: " + diff_in_days);
    // }

    public void showPopup(int foodId)
    {
        int[] popupPrices = new int[] {
            hayPrize,
            grassPrize,
            meatPrize,
            fishPrize,
            bananaPrize,
            milkPrize,
            bambooPrize,
            honeyPrize,
            magicSpellPrize
        };
        currentFoodPrice = popupPrices[foodId];

        if (coinManagerScript.Instance.getCoins() < currentFoodPrice) { // check if the player has enough coins
            showInsufficientCoinsPopup();
        } else { // show the purchase confirmation popup
            string[] popupTexts = new string[] {
                "Purchase Hay?",
                "Purchase Grass?",
                "Purchase Meat?",
                "Purchase Fish?",
                "Purchase Banana?",
                "Purchase Milk?",
                "Purchase Bamboo?",
                "Purchase Honey?",
                "Purchase a Magic Spell?"
            };
            foodName.text = popupTexts[foodId];
            foodPrice.text = popupPrices[foodId].ToString();
            currentFoodId = foodId;
            confirmPopup.SetActive(true);
        }
    }

    public void buyFood()
    {
        // dicrease the coin
        coinManagerScript.Instance.removeCoins(currentFoodPrice);
        StartCoroutine(APIHubScript.Instance.add_food(currentFoodId));
        // add the animal to the inventory
        confirmPopup.SetActive(false);
    }

    public void cancelPurchase()
    {
        confirmPopup.SetActive(false);
        insufficientCoinsPopup.SetActive(false);
    }
    private void showInsufficientCoinsPopup()
    {
        float currentCoins = coinManagerScript.Instance.getCoins();
        TextMeshProUGUI coinsText = insufficientCoinsPopup.transform.Find("CurrentAmount").Find("NumCoins").GetComponent<TextMeshProUGUI>();
        coinsText.text = currentCoins.ToString("F2");
        insufficientCoinsPopup.SetActive(true);
    }
}
