/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntu.hrms.dto.TitleDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface TitleBeanLocal {

    List<TitleDTO> getAllTitles();

    void addTitle(TitleDTO title);

    TitleDTO getTitle(int id);

    TitleDTO getTitleByName(String descr);
    
}
