using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class playerProfileMainScript : MonoBehaviour
{
    // Start is called before the first frame update
    public Button saveButton;
    private PlayerProfileScript playerProfileScript;

    void Start()
    {
        playerProfileScript.GetPlayerProfile();
        saveButton.onClick.AddListener(() => playerProfileScript.UpdateProfile());
    }
}
