/*
 * hworld.cpp
 *
 *  Created on: Aug 9, 2012
 *      Author: depit
 */

// hworld.cpp
// Version using dynamic event routing

#include <wx/wx.h>

class MyApp : public wxApp
{
    virtual bool OnInit();
};

// MAIN ->
// IMPLEMENT_APP(MyApp)


class MyFrame : public wxFrame
{
public:
    MyFrame(const wxString& title, const wxPoint& pos, const wxSize& size);
    void OnQuit(wxCommandEvent& event);
    void OnAbout(wxCommandEvent& event);
};

enum
{
    ID_Quit=1,
    ID_About
};


bool MyApp::OnInit()
{
    MyFrame *frame = new MyFrame( _("Hello Teleman"), wxPoint(50, 50),
                                  wxSize(450, 350));

    frame->Connect( ID_Quit, wxEVT_COMMAND_MENU_SELECTED,
                    (wxObjectEventFunction) &MyFrame::OnQuit );
    frame->Connect( ID_About, wxEVT_COMMAND_MENU_SELECTED,
                    (wxObjectEventFunction) &MyFrame::OnAbout );

    frame->Show(true);
    SetTopWindow(frame);
    return true;
}

MyFrame::MyFrame(const wxString& title, const wxPoint& pos, const wxSize& size)
    : wxFrame( NULL, -1, title, pos, size )
{
    wxMenuBar *menuBar = new wxMenuBar;

    wxMenu *menuFile = new wxMenu;

    menuFile->Append( ID_About, _("&About...") );
    menuFile->AppendSeparator();
    menuFile->Append( ID_Quit, _("E&xit") );

    menuBar->Append(menuFile, _("&File") );

    SetMenuBar(menuBar);

    CreateStatusBar();

    SetStatusText( _("Poti sa faci asta sa mearga pe MacOS ?") );
}

void MyFrame::OnQuit(wxCommandEvent& WXUNUSED(event))
{
    Close(true);
}

void MyFrame::OnAbout(wxCommandEvent& WXUNUSED(event))
{
    wxMessageBox( _("Faci sa mearga asta si pe Mac ?"),
                  _("Salutari Lucian ... "),
                  wxOK|wxICON_INFORMATION, this );
}




