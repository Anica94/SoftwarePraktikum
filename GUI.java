		JMenuItem mntmFile = new JMenuItem("file");
		mnOpen.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(12));
				/*
				 * produce open dialog
				 * read file
				 * produce graph
				 */
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        chooser.setDefaultLocale(Locale.ENGLISH); 
		        chooser.setLocale(Locale.ENGLISH);
		        chooser.updateUI();
		        if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getName();
		           	try {
					graph = reader.read(directoryName + "\\" + fileName);
					typeOfGraph = reader.typeOfGraph();
		           	} catch (IOException e1) {
		           		//System.out.println("Error concerning file:\\n");
		           		e1.printStackTrace();
		           	}   
		        }
			}
		});
		
		
		JMenuItem mntmSaveas = new JMenuItem("save (as)");
		mnFile.add(mntmSaveas);
		mntmSaveas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(13));
				/*
				 * produce save dialog
				 * write file
				 */
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getName();
		        	
		        }
			}
		});
		
		JMenuItem mntmShow = new JMenuItem("show");
		mnFile.add(mntmShow);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("show edge weights");
		mnView.add(mntmNewMenuItem_1);
		
		JMenuItem mntmShowHideLabel = new JMenuItem("hide edge weights");
		mnView.add(mntmShowHideLabel);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
